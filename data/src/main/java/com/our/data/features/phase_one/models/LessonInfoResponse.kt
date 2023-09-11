package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.domain.features.phase_one.models.remote.LessonInfo

@Keep
data class LessonInfoResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("additionalInfo")
    val additionalInfo: String? = null,
    @SerializedName("teacherId")
    val teacherId: Int? = null,
    @SerializedName("pricePer40m")
    val pricePer40m: Int? = null,
    @SerializedName("pricePer60m")
    val pricePer60m: Int? = null
)

fun LessonInfoResponse.toDomain(): LessonInfo =
    LessonInfo(
        id ?: 0, additionalInfo ?: "", teacherId ?: 0, pricePer40m ?: 0, pricePer60m ?: 0
    )