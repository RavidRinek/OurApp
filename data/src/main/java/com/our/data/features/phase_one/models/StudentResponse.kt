package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.Student

@Keep
data class StudentResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("studentName")
    val studentName: String? = null,
    @SerializedName("studentLastName")
    val lastName: String? = null,
    @SerializedName("studentPhone")
    val phone: String? = null,
    @SerializedName("studentAvatar")
    val avatarUrl: String? = null
) : BaseResponse()

fun StudentResponse.toDomain(): Student =
    Student(
        id = id ?: 0,
        name = studentName ?: "",
        lastName = lastName ?: "",
        phoneNumber = phone ?: "",
        avatarUrl = avatarUrl ?: ""
    )