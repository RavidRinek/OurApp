package com.our.data.features.phase_one.datasources

import com.our.data.features.phase_one.models.LessonsResponse
import com.our.data.features.phase_one.models.SubjectBranchesResponse
import com.our.data.features.phase_one.models.SubjectsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhaseOneApiService {

    @GET(GET_SUBJECTS_END_POINT)
    suspend fun getSubjects(): Response<SubjectsResponse>

    @GET(GET_SUBJECT_BRANCHES_END_POINT)
    suspend fun getSubjectBranches(@Query("subjectId") subjectId: Int): Response<SubjectBranchesResponse>

    @GET(GET_LESSONS_END_POINT)
    suspend fun getLessons(@Query("subjectBranchId") subjectBranch: Int): Response<LessonsResponse>

    companion object {
        const val GET_SUBJECTS_END_POINT: String = "get-subjects"
        const val GET_SUBJECT_BRANCHES_END_POINT: String = "get-subject-branches"
        const val GET_LESSONS_END_POINT: String = "get-lessons-by-subject-branch-id"
    }
}