package com.our.data.features.phase_one.datasources

import com.our.data.base.datasources.BaseRemoteDataSource
import com.our.data.base.models.BaseResponse
import com.our.data.common.datasources.MockApiService
import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import javax.inject.Inject

class MockPhaseOneDataSourceImpl @Inject constructor(private val api: MockApiService) :
    BaseRemoteDataSource(), PhaseOneDataSource {

    override suspend fun getSubjects(): Result<BaseResponse> =
        safeApiCall(
            call = { api.getMockData(PhaseOneApiService.GET_SUBJECTS_END_POINT) },
            errorMessage = "Cant get any subjects"
        )

    override suspend fun getSubjectBranches(branchId: Int): Result<BaseResponse> =
        safeApiCall(
            call = { api.getMockData(PhaseOneApiService.GET_SUBJECT_BRANCHES_END_POINT) },
            errorMessage = "Cant get any subject branches"
        )

    override suspend fun getLessons(levels: String): Result<BaseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTeacherById(teacherId: Int): Result<BaseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun postTeacherCreateInfo(teacherInfo: Map<String, String>): Result<BaseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun postCreateFcmToken(): Result<BaseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun postTeacherInfo(updateTeacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo): Result<BaseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTeacherOrders(teacherId: Int): Result<BaseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun postStudentCreate(createStudent: PostStudentCreateUseCase.CreateStudent): Result<BaseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun postOrderLesson(orderLesson: PostOrderLessonUseCase.OrderInfo): Result<BaseResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getStudentById(studentId: Int): Result<BaseResponse> {
        TODO("Not yet implemented")
    }
}