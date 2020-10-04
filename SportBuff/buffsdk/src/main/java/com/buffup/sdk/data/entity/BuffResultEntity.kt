package com.buffup.sdk.data.entity

import com.google.gson.annotations.SerializedName

data class BuffResultEntity(
    @SerializedName("answers") val answers: List<AnswerEntity>,
    @SerializedName("author") val author: AuthorEntity,
    @SerializedName("client_id") val clientId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: Int,
    @SerializedName("language") val language: String,
    @SerializedName("priority") val priority: Int,
    @SerializedName("question") val question: QuestionEntity,
    @SerializedName("stream_id") val streamId: Int,
    @SerializedName("time_to_show") val timeToShow: Int
)