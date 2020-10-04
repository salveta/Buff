package com.buffup.sdk.data.entity

import com.google.gson.annotations.SerializedName

data class ImageEntity(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("url") val url: String
)