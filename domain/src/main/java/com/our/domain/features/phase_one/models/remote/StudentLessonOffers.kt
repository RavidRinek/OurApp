package com.our.domain.features.phase_one.models.remote

data class StudentLessonOffers(
    val lesson: Lesson,
    val lessonInfo: LessonInfo,
    val teacherProfile: TeacherProfile
)
