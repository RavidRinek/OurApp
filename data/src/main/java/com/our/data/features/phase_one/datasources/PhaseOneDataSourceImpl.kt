package com.our.data.features.phase_one.datasources

import com.our.data.base.datasources.BaseRemoteDataSource
import com.our.data.base.datasources.Prefs
import com.our.data.base.models.BaseResponse
import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import javax.inject.Inject

class PhaseOneDataSourceImpl @Inject constructor(
    private val api: PhaseOneApiService,
    private val prefs: Prefs
) :
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

    override suspend fun postCreateFcmToken(): Result<BaseResponse> =
        safeApiCall(
            call = {
/*
                api.postCreateToken(
                    token = prefs.getString(Prefs.FCM_TOKEN),
                    userId = prefs.getInt(Prefs.MEMBER_ID)
                )
*/
                api.postCreateToken(
                    postToken = PostToken(
                        token = prefs.getString(Prefs.FCM_TOKEN),
                        userId = prefs.getInt(Prefs.MEMBER_ID)
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
}

data class PostToken(val userId: Int, val token: String)