package com.iak.perstest.data.api

import com.google.gson.JsonObject
import com.iak.perstest.data.entity.MarkRequest
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val api: Api) : ApiHelper {
    override suspend fun getQuestions(): Response<JsonObject> {
        return api.questions()
    }

    override suspend fun getEvaluation(request: MarkRequest): Response<JsonObject> {
        return api.evaluate(request)
    }
}