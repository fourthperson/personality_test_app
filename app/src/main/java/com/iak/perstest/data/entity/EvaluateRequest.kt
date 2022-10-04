package com.iak.perstest.data.entity

import com.google.gson.annotations.SerializedName

data class EvaluateRequest(
    @SerializedName("answer_count")
    val answerCount: Int,
    @SerializedName("answers")
    val answers: String
)