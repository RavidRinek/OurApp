package com.our.domain.features.phase_one.models.remote

data class LessonInfo(
    val id: Int,
    val additionalInfo: String,
    val teacherId: Int,
    val pricePer40m: Int,
    val pricePer60m: Int
)