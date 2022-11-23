package com.our.data.base.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.data.base.errors.ErrorCode

@Keep
open class BaseResponse {
    @SerializedName("success")
    val isSuccess: Boolean = false
    @SerializedName("error_code")
    val error: ErrorCode? = null
    @SerializedName("message")
    val message: String? = null
}