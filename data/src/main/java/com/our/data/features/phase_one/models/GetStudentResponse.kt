package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.Student

@Keep
data class GetStudentResponse(
    @SerializedName("student")
    val student: StudentResponse? = null
): BaseResponse()

fun GetStudentResponse.toDomain(): Student =
    (student ?: StudentResponse()).toDomain()