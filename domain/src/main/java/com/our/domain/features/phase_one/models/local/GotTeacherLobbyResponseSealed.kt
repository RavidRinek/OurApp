package com.our.domain.features.phase_one.models.local

import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.TeacherProfile

sealed class GotTeacherLobbyResponseSealed

object NavToTeacherLobby : GotTeacherLobbyResponseSealed()

object GotTeacherError : GotTeacherLobbyResponseSealed()

data class GotTeacherInfo(val teacherProfile: TeacherProfile) : GotTeacherLobbyResponseSealed()

data class GotSubjectLevels(val subjects: List<Subject>) : GotTeacherLobbyResponseSealed()

data class GotTeacherLessons(val lessons: List<String>) : GotTeacherLobbyResponseSealed()