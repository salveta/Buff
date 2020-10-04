package com.buffup.sdk.domain.model

data class BuffResultModel(val answers: List<AnswerModel>,
                           val author: AuthorModel,
                           val clientId: Int,
                           val createdAt: String,
                           val id: Int,
                           val language: String,
                           val priority: Int,
                           val question: QuestionModel,
                           val streamId: Int,
                           val timeToShow: Int)