package com.our.app.features.phase_one.teacherlobby.container

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotFirstPageInfo
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.usecases.GetSubjectsLevelsKnowledgeUseCase
import com.our.domain.features.phase_one.usecases.GetTeacherByIdUseCase
import com.our.domain.features.phase_one.usecases.PostFCMTokenUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherCreateInfoUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherContainerViewModel : BaseViewModelImpl() {
    abstract val teacherLobbyResponseLiveData: LiveData<GotTeacherLobbyResponseSealed>
    abstract fun postTeacherCreateInfo(createInfo: Map<String, String>)
    abstract fun getTeacherById(id: Int)
    abstract fun getSubjectLevelsForTeacherKnowlageInfo()
    abstract fun postTeacherInfo(teacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo)
}

@HiltViewModel
class TeacherContainerViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val postTeacherCreateInfoUseCase: PostTeacherCreateInfoUseCase,
    private val getTeacherByIdUseCase: GetTeacherByIdUseCase,
    private val getSubjects: GetSubjectsLevelsKnowledgeUseCase,
    private val postTeacherInfoUseCase: PostTeacherInfoUseCase,
    private val postFCMTokenUseCase: PostFCMTokenUseCase
) : TeacherContainerViewModel() {
    override val teacherLobbyResponseLiveData = MutableLiveData<GotTeacherLobbyResponseSealed>()

    var firstTime = true

    override fun postTeacherCreateInfo(createInfo: Map<String, String>) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            val res = postTeacherCreateInfoUseCase.invoke(createInfo)
            if (res is GotFirstPageInfo) {
                prefs.putInt(Prefs.MEMBER_ID, res.teacherProfile.teacherId)
                postFCMTokenUseCase.invoke(Unit)
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

    override fun getSubjectLevelsForTeacherKnowlageInfo() {
        if (!firstTime) return
        firstTime = false
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            teacherLobbyResponseLiveData.postValue(getSubjects.invoke(Unit))
        }
    }

    override fun postTeacherInfo(teacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            teacherLobbyResponseLiveData.postValue(postTeacherInfoUseCase.invoke(teacherInfo))
        }
    }
}