package com.our.app.features.phase_one.student.find_lesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotStudentLobbyResponseSealed
import com.our.domain.features.phase_one.models.local.GotSubjects
import com.our.domain.features.phase_one.usecases.GetSubjectBranchesUseCase
import com.our.domain.features.phase_one.usecases.GetSubjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class StudentFindLessonViewModel : BaseViewModelImpl() {
    abstract val studentLobbyResponseLiveData: LiveData<GotStudentLobbyResponseSealed>
    abstract fun getSubjects()
    abstract fun getSubjectBranches(subjectId: Int)
}

@HiltViewModel
class StudentFindLessonViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val getSubjectsUseCase: GetSubjectsUseCase,
    private val getSubjectBranchesUseCase: GetSubjectBranchesUseCase,
) : StudentFindLessonViewModel() {

    override val studentLobbyResponseLiveData = MutableLiveData<GotStudentLobbyResponseSealed>()

    override fun getSubjects() {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            val res = getSubjectsUseCase.invoke(Unit)
            if (res is GotSubjects) {
                studentLobbyResponseLiveData.postValue(res)
            }
        }
    }

    override fun getSubjectBranches(subjectId: Int) {
        launch {
            studentLobbyResponseLiveData.postValue(getSubjectBranchesUseCase.invoke(subjectId))
        }
    }
}