package com.buffup.sdk.data.entity

import com.google.gson.annotations.SerializedName

data class AnswerEntity(
    @SerializedName("buff_id") val buffId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: Map<String, ImageEntity>,
    @SerializedName("title") val title: String
)