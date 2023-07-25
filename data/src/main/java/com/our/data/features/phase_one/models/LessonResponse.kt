package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.Lesson

@Keep
data class LessonResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("duringOfLesson")
    val durationInMin: Int? = null,
    @SerializedName("time")
    val time: String? = null,
    @SerializedName("rating")
    val ratingInPercentage: Int? = null,
    @SerializedName("subjectBranchId")
    val subjectBranchId: Int? = null,
    @SerializedName("subjectName")
    val subjectName: String? = null,
    @SerializedName("teacherId")
    val teacherId: Int? = null,
    @SerializedName("teacherName")
    val teacherName: String? = null,
    @SerializedName("subjectBranchName")
    val subjectBranchName: String? = null
) : BaseResponse()

fun LessonResponse.toDomain(): Lesson =
    Lesson(
        id = id ?: 0,
        price = price ?: 160.0,
        durationInMin = durationInMin ?: 45,
        time = time ?: "15:30",
        ratingInPercentage = ratingInPercentage ?: 71,
        subjectBranchId = subjectBranchId ?: 0,
        subjectName = subjectName ?: "Sponge Bob Square Pants",
        teacherId = teacherId ?: 0,
        teacherName = teacherName ?: "Elroy",
        subjectBranchName = subjectBranchName ?: "fdsfsd"
    )