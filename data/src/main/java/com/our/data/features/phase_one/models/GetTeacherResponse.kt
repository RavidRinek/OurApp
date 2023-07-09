package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.TeacherProfile

@Keep
data class GetTeacherResponse(
    @SerializedName("teacher")
    val teacherProfileResponse: TeacherProfileResponse? = null
) : BaseResponse()

fun GetTeacherResponse.toDomain(): TeacherProfile =
    (teacherProfileResponse ?: TeacherProfileResponse()).toDomain()
