package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.domain.features.phase_one.models.remote.LessonInfo

@Keep
data class LessonInfoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("additionalInfo")
    val additionalInfo: String,
    @SerializedName("teacherId")
    val teacherId: Int,
    @SerializedName("pricePer40m")
    val pricePer40m: Int,
    @SerializedName("pricePer60m")
    val pricePer60m: Int
)

fun LessonInfoResponse.toDomain(): LessonInfo =
    LessonInfo(
        id, additionalInfo, teacherId, pricePer40m, pricePer60m
    )