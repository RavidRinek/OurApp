package com.our.app.features.phase_one.studentlobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.domain.features.phase_one.models.local.GotStudentLobbyResponseSealed
import com.our.domain.features.phase_one.usecases.GetLessonsUseCase
import com.our.domain.features.phase_one.usecases.GetSubjectBranchesUseCase
import com.our.domain.features.phase_one.usecases.GetSubjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class StudentLobbyViewModel : BaseViewModelImpl() {
    abstract val studentLobbyResponseLiveData: LiveData<GotStudentLobbyResponseSealed>
    abstract fun getSubjects()
    abstract fun getSubjectBranches(subjectId: Int)
    abstract fun getLessons(levels: List<Int>)
}

@HiltViewModel
class StudentLobbyViewModelImpl @Inject constructor(
    private val getSubjectsUseCase: GetSubjectsUseCase,
    private val getSubjectBranchesUseCase: GetSubjectBranchesUseCase,
    private val getLessonsUseCase: GetLessonsUseCase,
) : StudentLobbyViewModel() {

    override val studentLobbyResponseLiveData = MutableLiveData<GotStudentLobbyResponseSealed>()

    override fun getSubjects() {
        launch {
            studentLobbyResponseLiveData.postValue(getSubjectsUseCase.invoke(Unit))
        }
    }

    override fun getSubjectBranches(subjectId: Int) {
        launch {
            studentLobbyResponseLiveData.postValue(getSubjectBranchesUseCase.invoke(subjectId))
        }
    }

    override fun getLessons(levels: List<Int>) {
        launch {
            var txt = ""
            levels.forEach {
                txt = "$txt$it,"
            }
            txt = txt.substring(0, txt.length - 1);
            studentLobbyResponseLiveData.postValue(getLessonsUseCase.invoke(txt))
        }
    }
}