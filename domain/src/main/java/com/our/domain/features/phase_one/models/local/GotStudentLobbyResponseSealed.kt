package com.our.domain.features.phase_one.models.local

import com.our.domain.features.phase_one.models.remote.Lesson
import com.our.domain.features.phase_one.models.remote.Student
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.SubjectBranch

sealed class GotStudentLobbyResponseSealed

object GotStudentError : GotStudentLobbyResponseSealed()

data class GotSubjects(val subjects: List<Subject>) : GotStudentLobbyResponseSealed()

data class GotSubjectBranches(val subjectBranches: List<SubjectBranch>) :
    GotStudentLobbyResponseSealed()

data class GotLessons(val lessons: List<Lesson>) : GotStudentLobbyResponseSealed()

data class GotCreatedStudent(val student: Student) : GotStudentLobbyResponseSealed()

object GotOrderedLesson : GotStudentLobbyResponseSealed()