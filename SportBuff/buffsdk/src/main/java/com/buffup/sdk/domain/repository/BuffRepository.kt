package com.buffup.sdk.domain.repository

import com.buffup.sdk.data.api.BuffError
import com.buffup.sdk.data.api.BuffResult
import com.buffup.sdk.domain.model.BuffResultModel

interface BuffRepository {

    suspend fun getBuff(buffId: Int): BuffResult<BuffError, BuffResultModel>

}