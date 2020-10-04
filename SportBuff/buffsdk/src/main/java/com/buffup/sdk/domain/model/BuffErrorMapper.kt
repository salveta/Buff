package com.buffup.sdk.domain.model

import com.buffup.sdk.data.api.BuffError
import com.buffup.sdk.data.entity.ErrorEntity

fun ErrorEntity.toBuffError(): BuffError {
    return when (code) {
        500 -> BuffError.ServerError()
        else -> BuffError.Unknown()
    }
}