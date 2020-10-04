package com.buffup.sdk.presentation.modelView

import com.buffup.sdk.domain.model.*

fun BuffResultModel.toModelView(): BuffResultModelView {
    return BuffResultModelView (
        answers = answers.map { it.toModelView () },
        author = author.toModelView (),
        question = question.toModelView (),
        timeToShow = timeToShow
    )
}

fun AnswerModel.toModelView (): AnswerModelView  {
    return AnswerModelView (
        id = id,
        title = title
    )
}

fun AuthorModel.toModelView (): AuthorModelView  {
    return AuthorModelView (
        firstName = firstName,
        image = image,
        lastName = lastName
    )
}

fun QuestionModel.toModelView (): QuestionModelView  {
    return QuestionModelView (
        title = title
    )
}