package com.our.app.features.phase_one.teacherlobby.container

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.models.local.GotTeacherPersonalInfo
import com.our.domain.features.phase_one.usecases.GetSubjectsLevelsKnowledgeUseCase
import com.our.domain.features.phase_one.usecases.GetTeacherByIdUseCase
import com.our.domain.features.phase_one.usecases.PostFCMTokenUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherContainerViewModel : BaseViewModelImpl() {
    abstract val teacherLobbyResponseLiveData: LiveData<GotTeacherLobbyResponseSealed>
    abstract fun getTeacherById(teacherId: Int)
}

@HiltViewModel
class TeacherContainerViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val getSubjects: GetSubjectsLevelsKnowledgeUseCase,
    private val postTeacherInfoUseCase: PostTeacherInfoUseCase,
    private val getTeacherByIdUseCase: GetTeacherByIdUseCase,
    private val postFCMTokenUseCase: PostFCMTokenUseCase
) : TeacherContainerViewModel() {
    override val teacherLobbyResponseLiveData = MutableLiveData<GotTeacherLobbyResponseSealed>()

    override fun getTeacherById(teacherId: Int) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            val res = getTeacherByIdUseCase.invoke(prefs.getInt(Prefs.MEMBER_ID))
            if (res is GotTeacherPersonalInfo) {
                teacherLobbyResponseLiveData.postValue(res)
            }
        }
    }
}