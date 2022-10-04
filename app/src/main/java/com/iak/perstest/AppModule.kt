package com.iak.perstest

import android.app.Application
import com.google.gson.Gson
import com.iak.perstest.data.data_source.LocalDataSource
import com.iak.perstest.data.data_source.LocalDataSourceImpl
import com.iak.perstest.data.data_source.RemoteDataSource
import com.iak.perstest.data.data_source.RemoteDataSourceImpl
import com.iak.perstest.data.data_source.api.Api
import com.iak.perstest.data.data_source.db.AppDatabase
import com.iak.perstest.data.data_source.db.PastQuizDao
import com.iak.perstest.data.repository.QuizRepositoryImpl
import com.iak.perstest.data.repository.TestRepositoryImpl
import com.iak.perstest.domain.repo.QuizRepo
import com.iak.perstest.domain.repo.TestRepo
import com.iak.perstest.domain.use_case.AddPastTestUseCase
import com.iak.perstest.domain.use_case.GetEvaluationUseCase
import com.iak.perstest.domain.use_case.GetPastTestsUseCase
import com.iak.perstest.domain.use_case.GetQuestionsUseCase
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
        return BuildConfig.BASE_URL
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
    fun provideRemoteDataSource(api: Api): RemoteDataSource {
        return RemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideTestRepository(remoteDataSource: RemoteDataSource): TestRepo {
        return TestRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetQuestionsUseCase(testRepo: TestRepo): GetQuestionsUseCase {
        return GetQuestionsUseCase(testRepo)
    }

    @Provides
    @Singleton
    fun providesGetEvaluationUseCase(testRepo: TestRepo): GetEvaluationUseCase {
        return GetEvaluationUseCase(testRepo)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Provides
    @Singleton
    fun providesPastQuizDao(db: AppDatabase): PastQuizDao {
        return db.pastQuizDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(pastQuizDao: PastQuizDao): LocalDataSource {
        return LocalDataSourceImpl(pastQuizDao)
    }

    @Provides
    @Singleton
    fun provideQuizRepo(localDataSource: LocalDataSource): QuizRepo {
        return QuizRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideAddPastTestUseCase(quizRepo: QuizRepo): AddPastTestUseCase {
        return AddPastTestUseCase(quizRepo)
    }

    @Provides
    @Singleton
    fun provideGetPastTestsUseCase(quizRepo: QuizRepo): GetPastTestsUseCase {
        return GetPastTestsUseCase(quizRepo)
    }
}