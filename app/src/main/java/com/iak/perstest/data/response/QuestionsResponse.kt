package com.iak.perstest.data.response

data class QuestionsResponse(
    val `data`: List<QuestionData>,
    val status: Int
)

data class QuestionData(
    val created_on: String,
    val id: Int,
    val text: String
)