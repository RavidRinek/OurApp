package com.our.domain.features.phase_one.models.remote

data class TeacherOrder(
    val id: Int,
    val student: Student,
    val lesson: Lesson,
    val teacher: TeacherProfile,
    val subject: Subject,
    val videoUrl: String
) : java.io.Serializable