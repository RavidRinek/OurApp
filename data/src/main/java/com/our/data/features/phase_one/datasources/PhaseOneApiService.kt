package com.our.data.features.phase_one.datasources

import com.our.data.features.phase_one.models.FireBaseResponse
import com.our.data.features.phase_one.models.LessonsResponse
import com.our.data.features.phase_one.models.SubjectBranchesResponse
import com.our.data.features.phase_one.models.SubjectsResponse
import com.our.data.features.phase_one.models.TeacherProfileResponse
import retrofit2.Response
import retrofit2.http.*

interface PhaseOneApiService {

    @GET(GET_SUBJECTS_END_POINT)
    suspend fun getSubjects(): Response<SubjectsResponse>

    @GET(GET_SUBJECT_BRANCHES_END_POINT)
    suspend fun getSubjectBranches(@Query("subjectId") subjectId: Int): Response<SubjectBranchesResponse>

    @GET(GET_LESSONS_END_POINT)
    suspend fun getLessons(@Query("subjectBranchId") subjectBranch: Int): Response<LessonsResponse>

    @GET(GET_TEACHER_BY_ID)
    suspend fun getTeacherById(@Query("teacherId") teacherId: Int): Response<TeacherProfileResponse>

    @POST(POST_TEACHER_CREATE_INFO)
    suspend fun postTeacherCreateInfo(
        @Header("Content-Type") fsd: String = "application/json",
        @Body createInfo: Map<String, String>
    ): Response<TeacherProfileResponse>

    @POST(POST_CREATE_TOKEN)
    suspend fun postCreateToken(
        @Header("Content-Type") fsd: String = "application/json",
        @Body firebase : Map<String, String>
    ): Response<FireBaseResponse>

    companion object {
        const val GET_SUBJECTS_END_POINT: String = "get-subjects"
        const val GET_SUBJECT_BRANCHES_END_POINT: String = "get-subject-branches"
        const val GET_LESSONS_END_POINT: String = "get-lessons-by-subject-branch-id"
        const val GET_TEACHER_BY_ID: String = "get-teacher"
        const val POST_TEACHER_CREATE_INFO: String = "create-teacher"
        const val POST_CREATE_TOKEN: String = "create-token"

    }
}