package com.our.data.features.phase_one.models

import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.Lesson
import com.our.domain.features.phase_one.models.remote.StudentLessonOffers

data class LessonsResponse(
    @SerializedName("lessons")
    val lessons: List<StudentLessonOffersResponse>? = null
): BaseResponse()

fun LessonsResponse.toDomain(): List<StudentLessonOffers> =
    lessons?.map { it.toDomain() } ?: listOf()
