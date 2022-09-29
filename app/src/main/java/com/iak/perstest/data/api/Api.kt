package com.iak.perstest.data.api

import com.google.gson.JsonObject
import com.iak.perstest.data.entity.MarkRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("questions")
    suspend fun questions(): Response<JsonObject>

    @POST("evaluate")
    suspend fun evaluate(@Body request: MarkRequest): Response<JsonObject>
}