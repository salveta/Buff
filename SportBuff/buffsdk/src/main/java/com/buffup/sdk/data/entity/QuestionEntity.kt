package com.buffup.sdk.data.entity

import com.google.gson.annotations.SerializedName

data class QuestionEntity(
    @SerializedName("category")  val category: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
)