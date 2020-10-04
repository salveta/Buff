package com.buffup.sdk.domain.model

data class AnswerModel(
    val buffId: Int,
    val id: Int,
    val image: Map<String, ImageModel>,
    val title: String)