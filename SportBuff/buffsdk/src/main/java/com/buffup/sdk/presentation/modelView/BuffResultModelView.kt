package com.buffup.sdk.presentation.modelView

data class BuffResultModelView(val answers: List<AnswerModelView>,
                               val author: AuthorModelView,
                               val question: QuestionModelView,
                               val timeToShow: Int)