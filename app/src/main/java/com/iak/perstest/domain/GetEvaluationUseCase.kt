package com.iak.perstest.domain

import com.google.gson.JsonObject
import com.iak.perstest.data.entity.MarkRequest
import com.iak.perstest.data.repository.QuestionRepository
import retrofit2.Response
import javax.inject.Inject

class GetEvaluationUseCase @Inject constructor(private val repository: QuestionRepository) {
    suspend operator fun invoke(request: JsonObject): Response<JsonObject> {
        return repository.getEvaluation(MarkRequest.fromJsonObject(request))
    }
}