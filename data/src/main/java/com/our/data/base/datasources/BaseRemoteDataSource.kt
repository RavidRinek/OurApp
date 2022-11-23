package com.our.data.base.datasources

import com.our.data.base.models.BaseResponse
import retrofit2.Response
import timber.log.Timber
import java.io.IOException


abstract class BaseRemoteDataSource {

    suspend fun <T : BaseResponse> safeApiCall(call: suspend () -> Response<T>,
                                               errorMessage: String): com.our.domain.base.models.Result<T> {
        return try {
            call().safeApiResponseHandling(errorMessage)
        } catch (e: Exception) {
            Timber.e(e)
            com.our.domain.base.models.Result.Error(IOException(errorMessage, e.cause))
        }
    }

    private fun <T : BaseResponse> Response<T>.safeApiResponseHandling(errorMessage: String? = null): com.our.domain.base.models.Result<T> {
        if (this.isSuccessful) {
            val body = this.body()
            if (body != null) {
                return if (true) {
                    com.our.domain.base.models.Result.Success(body)
                } else {
                    handleErrorResponse(body)
                }
            }
        }
        val message = "${errorMessage?.plus(":") ?: ""} ${this.code()} ${this.message()}"
        return com.our.domain.base.models.Result.Error(IOException(message))
    }

    private fun <T : BaseResponse> handleErrorResponse(body: T): com.our.domain.base.models.Result<T> {
        val message = body.message ?: "Unknown error."
        return com.our.domain.base.models.Result.Error(com.our.data.base.errors.HttpException(body.error, message))
    }
}