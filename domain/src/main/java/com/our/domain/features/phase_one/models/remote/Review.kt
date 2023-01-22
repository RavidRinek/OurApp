package com.our.domain.features.phase_one.models.remote

data class Review(
    val id: Int,
    val teacherId: Int,
    val userId: Int,
    val description: String,
    val rating: Int,
    val like: Int
)