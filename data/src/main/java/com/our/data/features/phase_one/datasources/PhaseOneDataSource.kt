package com.our.data.features.phase_one.datasources

import com.our.data.base.models.BaseResponse
import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase

interface PhaseOneDataSource {
    suspend fun getSubjects(): Result<BaseResponse>
    suspend fun getSubjectBranches(subjectId: Int): Result<BaseResponse>
    suspend fun getLessons(levels: String): Result<BaseResponse>
    suspend fun getTeacherById(teacherId: Int): Result<BaseResponse>
    suspend fun postTeacherCreateInfo(teacherInfo: Map<String, String>): Result<BaseResponse>
    suspend fun postCreateFcmToken(): Result<BaseResponse>
    suspend fun postTeacherInfo(updateTeacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo): Result<BaseResponse>
    suspend fun getTeacherOrders(teacherId: Int): Result<BaseResponse>
    suspend fun postStudentCreate(createStudent: PostStudentCreateUseCase.CreateStudent): Result<BaseResponse>
    suspend fun postOrderLesson(orderLesson: PostOrderLessonUseCase.OrderInfo): Result<BaseResponse>
}