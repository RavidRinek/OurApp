package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.SubjectBranch

@Keep
data class SubjectBranchesResponse(
    @SerializedName("subjectBranches") val subjectBranches: List<SubjectBranchResponse>? = null
) : BaseResponse()

fun SubjectBranchesResponse.toDomain(): List<SubjectBranch> =
    (subjectBranches ?: listOf()).map { it.toDomain() }
