package com.our.data.features.phase_one.datasources

import com.our.data.base.datasources.BaseRemoteDataSource
import com.our.data.base.models.BaseResponse
import com.our.domain.base.models.Result
import javax.inject.Inject

class PhaseOneDataSourceImpl @Inject constructor(private val api: PhaseOneApiService) :
    BaseRemoteDataSource(), PhaseOneDataSource {

    override suspend fun getSubjects(): Result<BaseResponse> =
        safeApiCall(
            call = { api.getSubjects() },
            errorMessage = "Cant get any subjects"
        )

    override suspend fun getSubjectBranches(subjectId: Int): Result<BaseResponse> =
        safeApiCall(
            call = { api.getSubjectBranches(subjectId) },
            errorMessage = "Cant get any subject branches"
        )

    override suspend fun getLessons(subjectBranchId: Int): Result<BaseResponse> =
        safeApiCall(
            call = { api.getLessons(subjectBranchId) },
            errorMessage = "Cant find any lessons by this branch id"
        )

    override suspend fun getTeacherById(teacherId: Int): Result<BaseResponse> {
        TODO("Not yet implemented")
    }
}