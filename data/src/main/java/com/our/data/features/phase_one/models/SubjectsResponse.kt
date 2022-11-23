package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.Subject

@Keep
data class SubjectsResponse(
    @SerializedName("subjects") val subjects: List<SubjectResponse>? = null
) : BaseResponse()

fun SubjectsResponse.toDomain(): List<Subject> =
    (subjects ?: listOf()).map { it.toDomain() }