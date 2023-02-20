package com.our.domain.features.phase_one.models.remote

data class TeacherProfile(
    val teacherAddress: Int,
    val teacherAvatar: String,
    val teacherBirthday: Int,
    val teacherId: Int,
    val teacherLastName: String,
    val teacherMail: String,
    val teacherName: String,
    val teacherPhone: String,
    val teacherRating: Int,
    val teacherSecondPhone: String,
    val teacherSex: Int,
    val reviews: List<Review>,
    val subjects: List<Subject>,
    val profileGallery: List<TeacherGallery>
)
