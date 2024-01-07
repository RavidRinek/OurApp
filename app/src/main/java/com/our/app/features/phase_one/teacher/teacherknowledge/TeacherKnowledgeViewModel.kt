package com.our.app.features.phase_one.teacher.teacherknowledge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.usecases.GetSubjectsLevelsKnowledgeUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherKnowledgeViewModel : BaseViewModelImpl() {
    abstract val teacherLobbyResponseLiveData: LiveData<GotTeacherLobbyResponseSealed>
    abstract fun getSubjectLevelsForTeacherKnowlageInfo()
    abstract fun completeTeacherFullRegistration(teacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo)
}

@HiltViewModel
class TeacherKnowledgeViewModelImpl @Inject constructor(
    private val getSubjects: GetSubjectsLevelsKnowledgeUseCase,
    private val postTeacherInfoUseCase: PostTeacherInfoUseCase,
) : TeacherKnowledgeViewModel() {
    override val teacherLobbyResponseLiveData = MutableLiveData<GotTeacherLobbyResponseSealed>()

    init {
        getSubjectLevelsForTeacherKnowlageInfo()
    }

    override fun getSubjectLevelsForTeacherKnowlageInfo() {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            teacherLobbyResponseLiveData.postValue(getSubjects.invoke(Unit))
        }
    }

    override fun completeTeacherFullRegistration(teacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            teacherLobbyResponseLiveData.postValue(postTeacherInfoUseCase.invoke(teacherInfo))
        }
    }
}