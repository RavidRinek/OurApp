package com.our.data.features.phase_one.datasources

import com.our.data.base.models.BaseResponse
import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase

interface PhaseOneDataSource {
    suspend fun getSubjects(): Result<BaseResponse>
    suspend fun getSubjectBranches(subjectId: Int): Result<BaseResponse>
    suspend fun getLessons(subjectBranchId: Int): Result<BaseResponse>
    suspend fun getTeacherById(teacherId: Int): Result<BaseResponse>
    suspend fun postTeacherCreateInfo(teacherInfo: Map<String, String>): Result<BaseResponse>
    suspend fun postCreateFcmToken(): Result<BaseResponse>
    suspend fun postTeacherInfo(updateTeacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo): Result<BaseResponse>
}