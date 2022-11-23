package com.our.data.common.datasources

import com.our.data.base.models.BaseResponse
import com.our.data.features.phase_one.models.SubjectsResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MockApiService {

    @FormUrlEncoded
    @POST("/get-mock-data")
    suspend fun getMockData(
        @Field("endPoint") endPoint: String,
        @Field("state") state: String = "",
        @Field("byId") byId: String = ""
    ): Response<BaseResponse>
}