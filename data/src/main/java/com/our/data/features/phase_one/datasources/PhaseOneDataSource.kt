package com.our.data.features.phase_one.datasources

import com.our.data.base.models.BaseResponse
import com.our.domain.base.models.Result

interface PhaseOneDataSource {
    suspend fun getSubjects(): Result<BaseResponse>
    suspend fun getSubjectBranches(subjectId: Int): Result<BaseResponse>
    suspend fun getLessons(subjectBranchId: Int): Result<BaseResponse>
}