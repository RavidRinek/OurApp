package com.our.app.features.phase_one.teacherlobby.teacher_lobby

import com.our.app.base.BaseViewModelImpl
import com.our.data.base.datasources.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherLobbyViewModel : BaseViewModelImpl() {
    abstract fun getTeacherUpcomingLessons()
}

@HiltViewModel
class TeacherLobbyViewModelImpl @Inject constructor(
    private val prefs: Prefs
) : TeacherLobbyViewModel() {

    override fun getTeacherUpcomingLessons() {
        val teacherId = prefs.getInt(Prefs.MEMBER_ID)
    }
}