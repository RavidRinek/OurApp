package com.our.domain.features.phase_one.models.local

import com.our.domain.features.phase_one.models.remote.TeacherProfile

sealed class GotTeacherLobbyResponseSealed

object GotTeacherError : GotTeacherLobbyResponseSealed()

data class GotTeacherCreateInfo(val teacherProfile: TeacherProfile): GotTeacherLobbyResponseSealed()