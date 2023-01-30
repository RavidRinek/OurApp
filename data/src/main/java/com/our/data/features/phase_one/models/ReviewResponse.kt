package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.our.domain.features.phase_one.models.remote.Review

@Keep
data class ReviewResponse(
    val id: Int? = null,
    val teacherId: Int? = null,
    val userId: Int? = null,
    val description: String? = null,
    val rating: Int? = null,
    val like: Int? = null
)

fun ReviewResponse.toDomain(): Review =
    Review(
        id = id ?: 0,
        teacherId = teacherId ?: 0,
        userId = userId ?: 0,
        description = description ?: "",
        rating = rating ?: 0,
        like = like ?: 0
    )