package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.domain.features.phase_one.models.remote.TeacherGallery

@Keep
data class TeacherGalleryResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("url")
    val imgUrl: String? = null,
    @SerializedName("teacherId")
    val teacherId: Int? = null
)

fun TeacherGalleryResponse.toDomain(): TeacherGallery =
    TeacherGallery(
        id = id ?: 0,
        imgUrl = imgUrl ?: "",
        teacherId = teacherId?: 0
    )
