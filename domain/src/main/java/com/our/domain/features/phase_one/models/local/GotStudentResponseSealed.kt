package com.our.domain.features.phase_one.models.local

import com.our.domain.features.phase_one.models.remote.Student
import com.our.domain.features.phase_one.models.remote.StudentLessonOffers
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.SubjectBranch
import com.our.domain.features.phase_one.models.remote.TeacherOrder

sealed class GotStudentResponseSealed

object GotStudentError : GotStudentResponseSealed()

data class GotSubjects(val subjects: List<Subject>) : GotStudentResponseSealed()

data class GotSubjectBranches(val subjectBranches: List<SubjectBranch>) :
    GotStudentResponseSealed()

data class GotLessons(val lessons: List<StudentLessonOffers>) : GotStudentResponseSealed()

data class GotCreatedStudent(val student: Student) : GotStudentResponseSealed()

object GotOrderedLesson : GotStudentResponseSealed()

data class GotStudentPersonalInfo(val student: Student): GotStudentResponseSealed()

    data class GotStudentUpcomingLessons(val upcomingLessons: List<TeacherOrder>): GotStudentResponseSealed()