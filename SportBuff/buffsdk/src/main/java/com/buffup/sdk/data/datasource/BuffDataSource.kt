package com.buffup.sdk.data.datasource

import com.buffup.sdk.data.api.BuffResult
import com.buffup.sdk.data.entity.BuffResultEntity
import com.buffup.sdk.data.entity.ErrorEntity

interface BuffDataSource {

    suspend fun getProducts(buffId: Int): BuffResult<ErrorEntity, BuffResultEntity>

}