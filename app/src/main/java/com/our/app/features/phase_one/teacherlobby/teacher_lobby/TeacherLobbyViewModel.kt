package com.our.app.features.phase_one.teacherlobby.teacher_lobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.usecases.GetTeacherOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherLobbyViewModel : BaseViewModelImpl() {
    abstract val teacherLobbyResponseLiveData: LiveData<GotTeacherLobbyResponseSealed>
    abstract fun getTeacherUpcomingLessons()
}

@HiltViewModel
class TeacherLobbyViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val getTeacherOrdersUseCase: GetTeacherOrdersUseCase
) : TeacherLobbyViewModel() {

    override val teacherLobbyResponseLiveData = MutableLiveData<GotTeacherLobbyResponseSealed>()

    init {
        getTeacherUpcomingLessons()
    }

    override fun getTeacherUpcomingLessons() {
        val teacherId = prefs.getInt(Prefs.MEMBER_ID)
        launch {
            teacherLobbyResponseLiveData.postValue(getTeacherOrdersUseCase.invoke(33))
        }
    }
}