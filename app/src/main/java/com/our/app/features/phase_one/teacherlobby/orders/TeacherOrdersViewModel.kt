package com.our.app.features.phase_one.teacherlobby.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.usecases.GetTeacherOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherOrdersViewModel : BaseViewModelImpl() {
    abstract val uiStateLiveData: LiveData<UiState>
    abstract fun getTeacherOrders(id: Int)
}

@HiltViewModel
class TeacherOrdersViewModelImpl @Inject constructor(
    private val getTeacherOrdersUseCase: GetTeacherOrdersUseCase
) : TeacherOrdersViewModel() {
    override val uiStateLiveData = MutableLiveData<UiState>()

    override fun getTeacherOrders(id: Int) {
        launch {
            when (val res = getTeacherOrdersUseCase.invoke(id)) {
                is Result.Error -> TODO()
                is Result.Success -> {
                    uiStateLiveData.postValue(
                        uiStateLiveData.value?.copy(
                            a = "fsdfsd"
                        )
                    )
                }
            }
        }
    }
}

data class UiState(
    val a: String
)