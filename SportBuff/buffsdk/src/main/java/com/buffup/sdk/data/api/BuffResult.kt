package com.buffup.sdk.data.api

sealed class BuffResult<out Failure, out Success> {

    data class Failure<out Failure>(val failure: Failure) : BuffResult<Failure, Nothing>()

    data class Success<out Success>(val success: Success) : BuffResult<Nothing, Success>()

    val isSuccess get() = this is BuffResult.Success<Success>
    val isFailure get() = this is BuffResult.Failure<Failure>

}

fun <Failure, Success, T> BuffResult<Failure, Success>.fold(failure: (Failure) -> T, success: (Success) -> T): T =
    when (this) {
        is BuffResult.Failure -> failure(this.failure)
        is BuffResult.Success -> success(this.success)
    }