package com.our.data.features.phase_one.datasources

import com.our.data.base.models.BaseResponse
import com.our.data.features.phase_one.models.*
import com.our.data.features.phase_one.models.FireBaseResponse
import com.our.data.features.phase_one.models.LessonsResponse
import com.our.data.features.phase_one.models.SubjectBranchesResponse
import com.our.data.features.phase_one.models.SubjectsResponse
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import retrofit2.Response
import retrofit2.http.*

interface PhaseOneApiService {

    @GET(GET_SUBJECTS_END_POINT)
    suspend fun getSubjects(): Response<SubjectsResponse>

    @GET(GET_SUBJECT_BRANCHES_END_POINT)
    suspend fun getSubjectBranches(@Query("subjectId") subjectId: Int): Response<SubjectBranchesResponse>

    @GET("$GET_LESSONS_END_POINT/{levels}")
    suspend fun getLessons(@Path("levels") levels: String, @Query("price") price: Double, @Query("lessonTimestamp") timestamp: Long): Response<LessonsResponse>

    @GET(GET_TEACHER_BY_ID)
    suspend fun getTeacherById(@Query("teacherId") teacherId: Int): Response<GetTeacherResponse>

    @POST(POST_TEACHER_CREATE_INFO)
    suspend fun postTeacherCreateInfo(
        @Header("Content-Type") fsd: String = "application/json",
        @Body createInfo: Map<String, String>
    ): Response<GetTeacherResponse>

    @POST(POST_CREATE_TOKEN)
    suspend fun postCreateToken(
        @Header("Content-Type") fsd: String = "application/json",
//        @Field("token") token: String,
//        @Field("userId") userId: Int
        @Body postToken: PostToken
    ): Response<FireBaseResponse>

    @POST(POST_TEACHER_INFO)
    suspend fun postTeacherInfo(
        @Header("Content-Type") fsd: String = "application/json",
        @Body teacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo
    ): Response<BaseResponse>

    @GET(GET_TEACHER_ORDERS)
    suspend fun getTeacherOrders(@Query("teacherId") teacherId: Int): Response<GetUpcomingLessons>

    @POST(POST_CREATE_STUDENT)
    suspend fun postCreateStudent(
        @Header("Content-Type") fsd: String = "application/json",
        @Body student: PostStudentCreateUseCase.CreateStudent
    ): Response<GetStudentResponse>

    @POST(POST_ORDER_LESSON)
    suspend fun postOrderLesson(
        @Header("Content-Type") fsd: String = "application/json",
        @Body lesson: PostOrderLessonUseCase.OrderInfo
    ): Response<BaseResponse>

    @GET(GET_STUDENT_BY_ID)
    suspend fun getStudentById(@Query("studentId") studentId: Int): Response<GetStudentResponse>

    @GET(GET_STUDENT_ORDERS)
    suspend fun getStudentOrders(@Query("studentId") studentId: Int): Response<GetUpcomingLessons>

    companion object {
        const val GET_SUBJECTS_END_POINT: String = "get-subjects"
        const val GET_SUBJECT_BRANCHES_END_POINT: String = "get-subject-branches"
        const val GET_LESSONS_END_POINT: String = "get-lessons-by-subject-id"
        const val GET_TEACHER_BY_ID: String = "get-teacher"
        const val POST_TEACHER_CREATE_INFO: String = "create-teacher"
        const val POST_CREATE_TOKEN: String = "create-token"
        const val POST_TEACHER_INFO: String = "update-teacher"
        const val GET_TEACHER_ORDERS: String = "get-order"
        const val POST_CREATE_STUDENT: String = "student"
        const val POST_ORDER_LESSON: String = "new-order"
        const val GET_LESSONS_BY_LEVEL_ID: String = "new-order"
        const val GET_STUDENT_BY_ID: String = "get-student"
        const val GET_STUDENT_ORDERS = "get-order-by-student-id"
    }
}