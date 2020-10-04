package com.buffup.sdk.data.entity

import com.google.gson.annotations.SerializedName

data class BuffResponseEntity(
    @SerializedName("result") val result: BuffResultEntity
)