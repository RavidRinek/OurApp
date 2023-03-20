package com.our.data.features.phase_one.models

import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.FirebaseToken
import com.our.domain.features.phase_one.models.remote.TeacherProfile

data class FireBaseResponse (
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("token")
    val token: String? = null
) : BaseResponse()

fun FireBaseResponse.toDomain(): FirebaseToken =
    FirebaseToken(

        id = id ?: 0,
        userId = userId ?: 0,
        token = token ?: ""

    )