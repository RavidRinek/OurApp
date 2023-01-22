package com.our.data.features.phase_one.models

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TeacherProfileResponse(
    @SerializedName("teacherAddress")
    val teacherAddress: Int? = null,
    @SerializedName("teacherAvatar")
    val teacherAvatar: String? = null,
    @SerializedName("teacherBirthday")
    val teacherBirthday: Int? = null,
    @SerializedName("teacherCreatedDate")
    val teacherCreatedDate: String? = null,
    @SerializedName("teacherId")
    val teacherId: Int? = null,
    @SerializedName("teacherLastName")
    val teacherLastName: String? = null,
    @SerializedName("teacherMail")
    val teacherMail: String? = null,
    @SerializedName("teacherName")
    val teacherName: String? = null,
    @SerializedName("teacherPhone")
    val teacherPhone: String? = null,
    @SerializedName("teacherRate")
    val teacherRate: Int? = null,
    @SerializedName("teacherRating")
    val teacherRating: Int? = null,
    @SerializedName("teacherSecondPhone")
    val teacherSecondPhone: String? = null,
    @SerializedName("teacherSex")
    val teacherSex: Int? = null,
    @SerializedName("teacherUpdateDate")
    val teacherUpdateDate: String? = null
)
