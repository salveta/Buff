package com.buffup.sdk.data.api

import com.buffup.sdk.data.entity.ErrorEntity
import com.buffup.sdk.data.entity.toErrorEntity
import retrofit2.HttpException

object ApiCall {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): BuffResult<ErrorEntity, T> {
        return try {
            val result = apiCall()
            BuffResult.Success(result)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                val errorEntity = throwable.toErrorEntity()
                BuffResult.Failure(errorEntity)
            } else {
                BuffResult.Failure(ErrorEntity.unknownEntity)
            }
        }
    }
}