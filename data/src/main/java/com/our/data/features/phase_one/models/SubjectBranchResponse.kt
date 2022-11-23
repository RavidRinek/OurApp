package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.models.BaseResponse
import com.our.domain.features.phase_one.models.remote.SubjectBranch

@Keep
data class SubjectBranchResponse(
    @SerializedName("subjectBranchId") val id: Int? = null,
    @SerializedName("subjectBranchName") val name: String? = null,
    @SerializedName("subjectBranchDescription") val description: String? = null
) : BaseResponse()

fun SubjectBranchResponse.toDomain(): SubjectBranch =
    SubjectBranch(
        id = id ?: 0,
        name = name ?: "",
        description = description ?: ""
    )