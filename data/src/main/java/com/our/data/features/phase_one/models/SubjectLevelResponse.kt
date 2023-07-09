package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.domain.features.phase_one.models.remote.SubjectLevel

@Keep
data class SubjectLevelResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("level")
    val index: Int? = null,
    @SerializedName("levelName")
    val name: String? = null
)

fun SubjectLevelResponse.toDomain(): SubjectLevel =
    SubjectLevel(
        id = id ?: 0,
        index = index ?: 0,
        name = name ?: ""
    )
