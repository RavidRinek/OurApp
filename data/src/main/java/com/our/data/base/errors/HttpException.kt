package com.our.data.base.errors

class HttpException(val error: ErrorCode?, message: String?) : Exception(message)