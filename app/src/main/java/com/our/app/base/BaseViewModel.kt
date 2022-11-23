package com.our.app.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.our.data.base.errors.HttpException
import com.our.domain.base.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface BaseViewModel {
    val loadingLiveData: LiveData<LoadingStatus>
}

open class BaseViewModelImpl() : ViewModel(), BaseViewModel {

    override val loadingLiveData = MutableLiveData<LoadingStatus>()

    protected fun <T : Any> launchWithResult(
        context: CoroutineContext = Dispatchers.IO,
        displayProgressType: DisplayProgressTypes = DisplayProgressTypes.NONE,
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        remoteCall: suspend () -> Result<T>
    ): Job {
        loadingLiveData.value = LoadingStatus.Loading(displayProgressType)
        return viewModelScope.launch(context) {
            remoteCall()
                .onSuccess {
                    loadingLiveData.postValue(LoadingStatus.LoadingSuccess)
                    onSuccess(it)
                }
                .onError {
                    handleError(it)
                    onError(it)
                }
        }
    }

    protected fun launch(
        context: CoroutineContext = Dispatchers.IO,
        displayProgressType: DisplayProgressTypes = DisplayProgressTypes.NONE,
        onError: (Throwable) -> Unit = {},
        remoteCall: suspend () -> Any?
    ): Job {
        loadingLiveData.value = LoadingStatus.Loading(displayProgressType)
        return viewModelScope.launch(context) {
            try {
                remoteCall()
                loadingLiveData.postValue(LoadingStatus.LoadingSuccess)
            } catch (exc: Throwable) {
                handleError(exc)
                onError(exc)
            }
        }
    }

    private fun handleError(exception: Throwable) {
        loadingLiveData.postValue(
            when (exception) {
                is HttpException -> handleHttpError(exception)
                else -> LoadingStatus.LoadingError(throwable = exception)
            }
        )
    }

    private fun handleHttpError(exc: HttpException): LoadingStatus.LoadingError {
        val errorMessage = when (exc.error) {
            else -> "R.string.error_try_again"
        }
        return LoadingStatus.LoadingError(errorMessage, exc)
    }
}