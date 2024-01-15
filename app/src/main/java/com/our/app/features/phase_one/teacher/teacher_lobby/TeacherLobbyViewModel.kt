package com.our.app.features.phase_one.teacher.teacher_lobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.data.base.datasources.Prefs
import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.models.remote.TeacherOrder
import com.our.domain.features.phase_one.usecases.GetTeacherOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherLobbyViewModel : BaseViewModelImpl() {
    abstract val teacherLobbyResponseLiveData: LiveData<LiveDataState>
    abstract fun getTeacherUpcomingLessons()
}

@HiltViewModel
class TeacherLobbyViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val getTeacherOrdersUseCase: GetTeacherOrdersUseCase
) : TeacherLobbyViewModel() {

    override val teacherLobbyResponseLiveData = MutableLiveData<LiveDataState>().apply {
        value = LiveDataState(
            uiState = UiState(
                teacherOrders = listOf()
            )
        )
    }

    init {
        getTeacherUpcomingLessons()
    }

    override fun getTeacherUpcomingLessons() {
        launch {
            getTeacherOrdersUseCase.invoke(prefs.getInt(Prefs.TEACHER_ID)).onSuccess {
                teacherLobbyResponseLiveData.postValue(
                    teacherLobbyResponseLiveData.value?.copy(
                        emitType = EmitType.TEACHER_ORDERS,
                        uiState = teacherLobbyResponseLiveData.value!!.uiState.copy(
                            teacherOrders = it
                        )
                    )
                )
            }
        }
    }
}

data class LiveDataState(
    val emitType: EmitType = EmitType.NONE,
    val uiState: UiState
)

enum class EmitType {
    NONE,
    TEACHER_ORDERS
}

data class UiState(
    val teacherOrders: List<TeacherOrder>
)