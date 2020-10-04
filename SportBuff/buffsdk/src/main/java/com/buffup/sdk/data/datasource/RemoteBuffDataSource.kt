package com.buffup.sdk.data.datasource

import com.buffup.sdk.data.api.ApiCall
import com.buffup.sdk.data.api.BuffApi
import com.buffup.sdk.data.api.BuffResult
import com.buffup.sdk.data.entity.BuffResultEntity
import com.buffup.sdk.data.entity.ErrorEntity

class RemoteBuffDataSource(private val api: BuffApi): BuffDataSource {

    override suspend fun getProducts(buffId: Int): BuffResult<ErrorEntity, BuffResultEntity> {
        return ApiCall.safeApiCall { api.getBuff(buffId).result }
    }
}