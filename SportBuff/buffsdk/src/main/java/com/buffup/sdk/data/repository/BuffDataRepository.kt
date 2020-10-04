package com.buffup.sdk.data.repository

import com.buffup.sdk.data.api.BuffError
import com.buffup.sdk.data.api.BuffResult
import com.buffup.sdk.data.api.fold
import com.buffup.sdk.data.datasource.RemoteBuffDataSource
import com.buffup.sdk.domain.model.BuffResultModel
import com.buffup.sdk.domain.model.toBuffError
import com.buffup.sdk.domain.model.toModel
import com.buffup.sdk.domain.repository.BuffRepository

class BuffDataRepository(private val remoteBuffDataSource: RemoteBuffDataSource): BuffRepository {

    override suspend fun getBuff(buffId: Int): BuffResult<BuffError, BuffResultModel> {
        val result = remoteBuffDataSource.getProducts(buffId)
        return result.fold(
            {errorEntity ->
                BuffResult.Failure(errorEntity.toBuffError())},
            {dataEntity ->
                BuffResult.Success(dataEntity.toModel())}
        )
    }
}