package com.our.data.features.phase_one.datasources

import com.our.data.base.datasources.BaseRemoteDataSource
import com.our.data.base.datasources.Prefs
import com.our.data.base.models.BaseResponse
import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import javax.inject.Inject

class PhaseOneDataSourceImpl @Inject constructor(
    private val api: PhaseOneApiService,
    private val prefs: Prefs
) : BaseRemoteDataSource(), PhaseOneDataSource {

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

    override suspend fun getLessons(levels: String, pricea: Double): Result<BaseResponse> {
        return safeApiCall(
            call = {
                api.getLessons(levels, pricea)
            },
            errorMessage = "Cant find any lessons by level branch id"
        )
    }

    override suspend fun getTeacherById(teacherId: Int): Result<BaseResponse> =
        safeApiCall(
            call = { api.getTeacherById(teacherId) },
            errorMessage = "Cant get any teacher by id: $teacherId"
        )

    override suspend fun postTeacherCreateInfo(teacherInfo: Map<String, String>): Result<BaseResponse> =
        safeApiCall(
            call = { api.postTeacherCreateInfo(createInfo = teacherInfo) },
            errorMessage = "Cant get any teacher by id: $teacherInfo"
        )

    override suspend fun postCreateFcmToken(id: Int): Result<BaseResponse> =
        safeApiCall(
            call = {
                api.postCreateToken(
                    postToken = PostToken(
                        token = prefs.getString(Prefs.FCM_TOKEN),
                        userId = id
                    )

                )
            },
            errorMessage = "Cant post token"
        )

    override suspend fun postTeacherInfo(updateTeacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo): Result<BaseResponse> =
        safeApiCall(
            call = { api.postTeacherInfo(teacherInfo = updateTeacherInfo) },
            errorMessage = "Cant update teacher info by: $updateTeacherInfo"
        )

    override suspend fun getTeacherOrders(teacherId: Int): Result<BaseResponse> =
        safeApiCall(
            call = { api.getTeacherOrders(teacherId) },
            errorMessage = "Cant get teacher orders by teacherId: $teacherId"
        )

    override suspend fun postStudentCreate(createStudent: PostStudentCreateUseCase.CreateStudent): Result<BaseResponse> =
        safeApiCall(
            call = { api.postCreateStudent(student = createStudent) },
            errorMessage = "Cant create user: $createStudent"
        )

    override suspend fun postOrderLesson(orderLesson: PostOrderLessonUseCase.OrderInfo): Result<BaseResponse> =
        safeApiCall(
            call = { api.postOrderLesson(lesson = orderLesson) },
            errorMessage = "Cant order lesson: $orderLesson"
        )

    override suspend fun getStudentById(studentId: Int): Result<BaseResponse> =
        safeApiCall(
            call = { api.getStudentById(studentId = studentId) },
            errorMessage = "Cant get student by id: $studentId"
        )

    override suspend fun getStudentOrders(studentId: Int): Result<BaseResponse> =
        safeApiCall(
            call = { api.getStudentOrders(studentId) },
            errorMessage = "Cant get student orders by id $studentId"
        )
}

data class PostToken(val userId: Int, val token: String)