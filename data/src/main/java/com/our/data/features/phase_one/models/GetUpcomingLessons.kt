package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.Oreder

@Keep
data class GetUpcomingLessons(
    @SerializedName("orders")
    val orders: List<UpcomingLessons>? = null
): BaseResponse()

fun GetUpcomingLessons.toDomain(): List<Oreder> =
    (orders ?: listOf()).map { it.toDomain() }
