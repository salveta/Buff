package com.buffup.sdk.domain.model

import com.buffup.sdk.data.entity.*

fun BuffResultEntity.toModel(): BuffResultModel{
    return BuffResultModel(
        answers = answers.map { it.toModel() },
        author = author.toModel(),
        clientId = clientId,
        createdAt = createdAt,
        id = id,
        language = language,
        priority = priority,
        question = question.toModel(),
        streamId = streamId,
        timeToShow = timeToShow
    )
}

fun AnswerEntity.toModel(): AnswerModel{
    return AnswerModel(
        buffId = buffId,
        id = id,
        image = image.mapValues { images -> images.value.toModel() },
        title = title
    )
}

fun ImageEntity.toModel(): ImageModel{
    return ImageModel(
       id = id,
        key = key,
        url = url
    )
}

fun AuthorEntity.toModel(): AuthorModel{
    return AuthorModel(
        firstName = firstName,
        image = image,
        lastName = lastName
    )
}

fun QuestionEntity.toModel(): QuestionModel{
    return QuestionModel(
        category = category,
        id = id,
        title = title
    )
}