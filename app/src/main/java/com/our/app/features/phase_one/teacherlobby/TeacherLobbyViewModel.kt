package com.our.app.features.phase_one.teacherlobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotTeacherInfo
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.usecases.GetSubjectsLevelsKnowledgeUseCase
import com.our.domain.features.phase_one.usecases.GetTeacherByIdUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherCreateInfoUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherLobbyViewModel : BaseViewModelImpl() {
    abstract val teacherLobbyResponseLiveData: LiveData<GotTeacherLobbyResponseSealed>
    abstract fun postTeacherCreateInfo(createInfo: Map<String, String>)
    abstract fun getTeacherById(id: Int)
    abstract fun getSubjectLevels()
    abstract fun postTeacherInfo(teacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo)
}

@HiltViewModel
class TeacherLobbyViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val postTeacherCreateInfoUseCase: PostTeacherCreateInfoUseCase,
    private val getTeacherByIdUseCase: GetTeacherByIdUseCase,
    private val getSubjects: GetSubjectsLevelsKnowledgeUseCase,
    private val postTeacherInfoUseCase: PostTeacherInfoUseCase
) : TeacherLobbyViewModel() {
    override val teacherLobbyResponseLiveData = MutableLiveData<GotTeacherLobbyResponseSealed>()

    override fun postTeacherCreateInfo(createInfo: Map<String, String>) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            val res = postTeacherCreateInfoUseCase.invoke(createInfo)
            if (res is GotTeacherInfo) {
                prefs.putInt(Prefs.MEMBER_ID, res.teacherProfile.teacherId)
            }
            teacherLobbyResponseLiveData.postValue(res)
        }
    }

    override fun getTeacherById(id: Int) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            teacherLobbyResponseLiveData.postValue(getTeacherByIdUseCase.invoke(id))
        }
    }

    override fun getSubjectLevels() {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ){
            teacherLobbyResponseLiveData.postValue(getSubjects.invoke(Unit))
        }
    }

    override fun postTeacherInfo(teacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ){
            teacherLobbyResponseLiveData.postValue(postTeacherInfoUseCase.invoke(teacherInfo))
        }
    }
}