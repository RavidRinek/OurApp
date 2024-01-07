package com.our.app.features.phase_one.student.student_lobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotStudentResponseSealed
import com.our.domain.features.phase_one.usecases.GetStudentUpcomingLessons
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class StudentLobbyViewModel : BaseViewModelImpl() {
    abstract val studentLobbyLiveData: LiveData<GotStudentResponseSealed>
}

@HiltViewModel
class StudentLobbyViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val getStudentUpcomingLessons: GetStudentUpcomingLessons
) : StudentLobbyViewModel() {

    override val studentLobbyLiveData = MutableLiveData<GotStudentResponseSealed>()

    init {
        getStudentUpcomingLessons()
    }

    private fun getStudentUpcomingLessons() {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            studentLobbyLiveData.postValue(getStudentUpcomingLessons.invoke(prefs.getInt(Prefs.STUDENT_ID)))
        }
    }

}