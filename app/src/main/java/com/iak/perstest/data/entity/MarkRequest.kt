package com.iak.perstest.data.entity

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class MarkRequest(
    @SerializedName("answer_count")
    val answerCount: Int,
    @SerializedName("answers")
    val answers: String
) {
    companion object {
        fun fromJsonObject(o: JsonObject): MarkRequest {
            return MarkRequest(answerCount = o.get("answer_count").asInt, answers = o.get("answers").asString)
        }
    }
}