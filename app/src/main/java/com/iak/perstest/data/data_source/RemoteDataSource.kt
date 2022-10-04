package com.iak.perstest.data.data_source

import com.iak.perstest.data.entity.EvaluateRequest
import com.iak.perstest.data.response.EvaluationResponse
import com.iak.perstest.data.response.QuestionsResponse
import retrofit2.Call

interface RemoteDataSource {
    fun getQuestions(): Call<QuestionsResponse>

    fun getEvaluation(request: EvaluateRequest): Call<EvaluationResponse>
}