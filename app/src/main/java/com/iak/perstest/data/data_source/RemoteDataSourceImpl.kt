package com.iak.perstest.data.data_source

import com.iak.perstest.data.data_source.api.Api
import com.iak.perstest.data.entity.EvaluateRequest
import com.iak.perstest.data.response.EvaluationResponse
import com.iak.perstest.data.response.QuestionsResponse
import retrofit2.Call
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: Api) : RemoteDataSource {
    override fun getQuestions(): Call<QuestionsResponse> {
        return api.questions()
    }

    override fun getEvaluation(request: EvaluateRequest): Call<EvaluationResponse> {
        return api.evaluate(request)
    }
}