package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.domain.features.phase_one.models.remote.StudentLessonOffers

@Keep
data class StudentLessonOffersResponse(
    @SerializedName("lesson")
    val lessonResponse: LessonResponse? = null,
    @SerializedName("lessonInfo")
    val lessonInfo: LessonInfoResponse? = null
)

fun StudentLessonOffersResponse.toDomain(): StudentLessonOffers =
    StudentLessonOffers(
        lesson = (lessonResponse ?: LessonResponse()).toDomain(),
        lessonInfo = (lessonInfo ?: LessonInfoResponse()).toDomain()
    )
