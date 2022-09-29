package com.iak.perstest.domain

import com.google.gson.JsonObject
import com.iak.perstest.data.repository.QuestionRepository
import retrofit2.Response
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(private val repository: QuestionRepository) {
    suspend operator fun invoke(): Response<JsonObject> {
        return repository.getQuestions()
    }
}