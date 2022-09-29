package com.iak.perstest.data.api

import com.google.gson.JsonObject
import com.iak.perstest.data.entity.MarkRequest
import retrofit2.Response

interface ApiHelper {
    suspend fun getQuestions(): Response<JsonObject>

    suspend fun getEvaluation(request: MarkRequest): Response<JsonObject>
}