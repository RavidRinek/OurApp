package com.our.data.features.phase_one.models

import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.Lesson

data class LessonsResponse(
    @SerializedName("lessons")
    val lessons: List<LessonResponse>? = null
): BaseResponse()

fun LessonsResponse.toDomain(): List<Lesson> =
    lessons?.map { it.toDomain() } ?: listOf()
