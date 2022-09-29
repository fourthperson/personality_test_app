package com.iak.perstest

import com.google.gson.Gson
import com.iak.perstest.data.api.Api
import com.iak.perstest.data.api.ApiHelper
import com.iak.perstest.data.api.ApiHelperImpl
import com.iak.perstest.data.repository.QuestionRepository
import com.iak.perstest.domain.GetQuestionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL;
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(logger).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper {
        return apiHelperImpl
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(apiHelper: ApiHelper): QuestionRepository {
        return QuestionRepository(apiHelper);
    }

    @Provides
    fun provideGetQuestionsUseCase(questionRepository: QuestionRepository): GetQuestionsUseCase {
        return GetQuestionsUseCase(questionRepository);
    }
}