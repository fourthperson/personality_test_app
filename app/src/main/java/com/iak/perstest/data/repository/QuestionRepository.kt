package com.iak.perstest.data.repository

import com.iak.perstest.data.api.ApiHelper
import com.iak.perstest.data.entity.MarkRequest
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getQuestions() = apiHelper.getQuestions()

    suspend fun getEvaluation(request: MarkRequest) = apiHelper.getEvaluation(request)
}