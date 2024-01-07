package com.our.app.features.phase_one.student.student_lobby

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
}

@HiltViewModel
class StudentLobbyViewModelImpl @Inject constructor(
) : StudentLobbyViewModel() {

    override val studentLobbyResponseLiveData = MutableLiveData<GotStudentLobbyResponseSealed>()
}