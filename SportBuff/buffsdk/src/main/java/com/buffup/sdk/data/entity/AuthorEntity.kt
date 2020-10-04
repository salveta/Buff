package com.buffup.sdk.data.entity

import com.google.gson.annotations.SerializedName

data class AuthorEntity(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("image") val image: String,
    @SerializedName("last_name") val lastName: String
)