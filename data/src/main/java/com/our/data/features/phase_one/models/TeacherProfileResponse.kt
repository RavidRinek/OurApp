package com.our.data.features.phase_one.models

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.TeacherProfile

@Keep
data class TeacherProfileResponse(
    @SerializedName("teacherAddress")
    val teacherAddress: Int? = null,
    @SerializedName("teacherAvatar")
    val teacherAvatar: String? = null,
    @SerializedName("teacherBirthday")
    val teacherBirthday: Int? = null,
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
    @SerializedName("teacherRating")
    val teacherRating: Int? = null,
    @SerializedName("teacherSecondPhone")
    val teacherSecondPhone: String? = null,
    @SerializedName("teacherSex")
    val teacherSex: Int? = null,
    @SerializedName("reviews")
    val reviews: List<ReviewResponse>? = null,
    @SerializedName("subjects")
    val subjects: List<SubjectResponse>? = null,
    @SerializedName("gallery")
    val profileGallery: List<TeacherGalleryResponse>? = null
) : BaseResponse()

fun TeacherProfileResponse.toDomain(): TeacherProfile =
    TeacherProfile(
        teacherAddress = teacherAddress ?: 0,
        teacherAvatar = teacherAvatar ?: "",
        teacherBirthday = teacherBirthday ?: 0,
        teacherId = teacherId ?: 0,
        teacherMail = teacherMail ?: "",
        teacherLastName = teacherLastName ?: "",
        teacherName = teacherName ?: "",
        teacherPhone = teacherPhone ?: "",
        teacherRating = teacherRating ?: 0,
        teacherSecondPhone = teacherSecondPhone ?: "",
        teacherSex = teacherSex ?: 0,
        reviews = (reviews ?: listOf()).map { it.toDomain() },
        subjects = (subjects ?: listOf()).map { it.toDomain() },
        profileGallery = (profileGallery ?: listOf()).map { it.toDomain() }
    )