package com.our.domain.features.phase_one.models.remote

data class Oreder(
    val id: Int,
    val student: Student,
    val lesson: Lesson,
    val teacher: TeacherProfile,
    val subject: Subject,
    val videoUrl: String,
    val lessonTimestamp:Long,
) : java.io.Serializable