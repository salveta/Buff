package com.buffup.sdk.domain.model

import com.buffup.sdk.data.entity.ImageEntity

data class AnswerModel(
    val buffId: Int,
    val id: Int,
    val image: Map<String, ImageEntity>,
    val title: String)