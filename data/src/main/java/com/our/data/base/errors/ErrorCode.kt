package com.our.data.base.errors

import com.google.gson.annotations.SerializedName

enum class ErrorCode {

    @SerializedName(value = "1000")
    ERROR_LOGIN,

    @SerializedName(value = "1001")
    ERROR_MEMBER_NOT_EXISTS,

    @SerializedName(value = "1002")
    ERROR_ACTION_NOT_ALLOWED,

    @SerializedName(value = "1006")
    ERROR_SOCIAL_ALREADY_IN_USE,

    @SerializedName(value = "1010")
    ERROR_USERNAME_ALREADY_IN_USE,

    @SerializedName(value = "1011")
    ERROR_INSTAGRAM_NOT_CONNECTED,

    @SerializedName(value = "1100")
    ERROR_UPLOAD_NOT_VALID,

    @SerializedName(value = "1103")
    ERROR_UPLOAD_TOO_LARGE,

    @SerializedName(value = "1104")
    ERROR_UPLOAD_FILE_NOT_ALLOWED,

    @SerializedName(value = "10000")
    ERROR_UPDATE,

    @SerializedName(value = "1105")
    ERROR_DATA,

    @SerializedName(value = "1500")
    BLOCKED_FROM_USER;
}
