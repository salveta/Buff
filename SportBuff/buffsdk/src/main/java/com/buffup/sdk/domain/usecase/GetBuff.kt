package com.buffup.sdk.domain.usecase

import com.buffup.sdk.data.api.BuffError
import com.buffup.sdk.data.api.fold
import com.buffup.sdk.domain.model.BuffResultModel
import com.buffup.sdk.domain.repository.BuffRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetBuff(private val buffRepository: BuffRepository) {

    suspend operator fun invoke(
        buffId: Int,
        onGeBuffSuccess: (data: BuffResultModel) -> Unit,
        onGetErrorBuff: (data: BuffError) -> Unit
    ) {

        val result = withContext(Dispatchers.IO){
            buffRepository.getBuff(buffId)
        }

        result.fold(
            failure = { error -> onGetErrorBuff(error) },
            success = { data -> onGeBuffSuccess(data) }
        )
    }
}