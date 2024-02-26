package com.our.app.features.phase_one.teacher.teacher_lobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotCompletedFullRegistration
import com.our.domain.features.phase_one.models.local.GotFcmToken
import com.our.domain.features.phase_one.models.local.GotSubjectLevelsForTeacherKnowledgeInfo
import com.our.domain.features.phase_one.models.local.GotTeacherError
import com.our.domain.features.phase_one.models.local.GotTeacherOrders
import com.our.domain.features.phase_one.models.local.GotTeacherPersonalInfo
import com.our.domain.features.phase_one.models.remote.Oreder
import com.our.domain.features.phase_one.models.remote.TeacherProfile
import com.our.domain.features.phase_one.usecases.GetTeacherByIdUseCase
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
    private val getTeacherOrdersUseCase: GetTeacherOrdersUseCase,
    private val getTeacherByIdUseCase: GetTeacherByIdUseCase
) : TeacherLobbyViewModel() {

    override val teacherLobbyResponseLiveData = MutableLiveData<LiveDataState>().apply {
        value = LiveDataState(
            uiState = UiState(
                teacherOrders = listOf(),
                teacherProfile = null
            )
        )
    }

    init {
        getTeacherUpcomingLessons()
        getTeacherById()
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

    private fun getTeacherById() {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            when (val res = getTeacherByIdUseCase.invoke(prefs.getInt(Prefs.TEACHER_ID))) {
                is GotTeacherPersonalInfo -> {
                    teacherLobbyResponseLiveData.postValue(
                        teacherLobbyResponseLiveData.value?.copy(
                            emitType = EmitType.TEACHER_INFO,
                            uiState = teacherLobbyResponseLiveData.value!!.uiState.copy(
                                teacherProfile = res.teacherProfile
                            )
                        )
                    )
                }

                else -> Unit
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
    TEACHER_INFO,
    TEACHER_ORDERS,
}

data class UiState(
    val teacherOrders: List<Oreder>,
    val teacherProfile: TeacherProfile? = null
)