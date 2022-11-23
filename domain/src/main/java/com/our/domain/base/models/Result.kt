package com.our.domain.base.models

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()

    val requireData: T get() = (this as Success<T>).data

    val successData: T? get() = (this as? Success<T>)?.data

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

    fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }

    fun onError(action: (Exception) -> Unit): Result<T> {
        if (this is Error) action(exception)
        return this
    }
}

/**
 * This is a mapper of result.
 *
 * In case if the Result successful - will be returned [Result.Success]
 * with [Result.Success.data] mapped by function [map]]
 *
 * In case if returned [Result.Error] - will be returned [Result.Error]
 * with [Result.Error.exception] of the source.
 *
 * In case if mapping is failed - will be returned [Result.Error]
 * with [Result.Error.exception] of the source.
 */
inline fun<IN: Any, OUT: Any> Result<IN>.map(mapper: (IN) -> OUT): Result<OUT> =
    try {
        when (this) {
            is Result.Success -> Result.Success(mapper(this.data))
            is Result.Error -> this
        }
    } catch (exc: Exception) {
        Result.Error(exc)
    }