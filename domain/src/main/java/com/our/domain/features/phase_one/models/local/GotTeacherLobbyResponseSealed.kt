package com.our.domain.features.phase_one.models.local

import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.TeacherProfile

sealed class GotTeacherLobbyResponseSealed

object FirstTeacherDetails : GotTeacherLobbyResponseSealed()

object GotTeacherError : GotTeacherLobbyResponseSealed()

data class GotFirstPageInfo(val teacherProfile: TeacherProfile) : GotTeacherLobbyResponseSealed()

data class GotSubjectLevelsForTeacherKnowledgeInfo(val subjects: List<Subject>) :
    GotTeacherLobbyResponseSealed()

object GotFcmToken : GotTeacherLobbyResponseSealed()

object GotTeacherOrders : GotTeacherLobbyResponseSealed()