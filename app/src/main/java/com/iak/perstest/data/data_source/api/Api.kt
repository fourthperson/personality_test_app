package com.iak.perstest.data.data_source.api

import com.iak.perstest.data.entity.EvaluateRequest
import com.iak.perstest.data.response.EvaluationResponse
import com.iak.perstest.data.response.QuestionsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("questions")
    fun questions(): Call<QuestionsResponse>

    @POST("evaluate")
    fun evaluate(@Body request: EvaluateRequest): Call<EvaluationResponse>
}