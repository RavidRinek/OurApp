package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.Subject

@Keep
data class SubjectResponse(
    @SerializedName("subjectId") val id: Int? = null,
    @SerializedName("subjectName") val name: String? = null,
    @SerializedName("urlImage") val imgUrl: String? = null
) : BaseResponse()

fun SubjectResponse.toDomain(): Subject =
    Subject(
        id = id ?: 0,
        name = name ?: "",
        imgUrl = imgUrl ?: ""
    )