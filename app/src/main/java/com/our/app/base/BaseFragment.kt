package com.our.app.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

abstract class BaseFragment<out VM : BaseViewModel>(layoutRes: Int) : Fragment(layoutRes) {

    protected abstract val viewModel: VM
    protected open fun setLoading(progressType: DisplayProgressTypes) = Unit
    protected open fun resultSuccess() = Unit
    protected open fun resultFailure(status: LoadingStatus.LoadingError) {
        showError(status)
    }

    /**
     * Basic handle of error - show the [Toast]
     * with error message: [errorRes]
     */
    private fun showError(status: LoadingStatus.LoadingError) {
        status.errorMessage?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    @CallSuper
    protected open fun observeData() {
        viewModel.loadingLiveData.observe { status ->
            when (status) {
                is LoadingStatus.Loading -> setLoading(status.data)
                is LoadingStatus.LoadingSuccess -> {
                    setLoading(DisplayProgressTypes.NONE)
                    resultSuccess()
                }
                is LoadingStatus.LoadingError -> {
                    setLoading(DisplayProgressTypes.NONE)
                    resultFailure(status)
                }
            }
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner) {
            observer(it)
        }
    }
}