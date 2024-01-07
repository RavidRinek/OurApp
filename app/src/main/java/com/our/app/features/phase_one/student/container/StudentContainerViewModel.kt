package com.our.app.features.phase_one.student.container

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotStudentLobbyResponseSealed
import com.our.domain.features.phase_one.models.local.GotStudentPersonalInfo
import com.our.domain.features.phase_one.usecases.GetStudentByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class StudentContainerViewModel : BaseViewModelImpl() {
    abstract val studentLobbyResponseLiveData: LiveData<GotStudentLobbyResponseSealed>
    abstract fun getStudentById(studentId: Int)
}

@HiltViewModel
class StudentContainerViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val getStudentByIdUseCase: GetStudentByIdUseCase
) : StudentContainerViewModel() {

    override val studentLobbyResponseLiveData = MutableLiveData<GotStudentLobbyResponseSealed>()

    override fun getStudentById(studentId: Int) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            val res = getStudentByIdUseCase.invoke(prefs.getInt(Prefs.STUDENT_ID))
            if (res is GotStudentPersonalInfo) {
                studentLobbyResponseLiveData.postValue(res)
            }
        }
    }
}