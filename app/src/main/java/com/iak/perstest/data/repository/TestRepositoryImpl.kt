package com.iak.perstest.data.repository

import com.iak.perstest.data.data_source.RemoteDataSource
import com.iak.perstest.data.entity.EvaluateRequest
import com.iak.perstest.domain.entity.Evaluation
import com.iak.perstest.domain.entity.Question
import com.iak.perstest.domain.repo.TestRepo
import timber.log.Timber
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    TestRepo {
    override fun getQuestions(): MutableList<Question>? {
        return try {
            val response = remoteDataSource.getQuestions().execute()
            if (response.isSuccessful && response.body() != null) {
                if (response.body()!!.status == 200) {
                    val questions = ArrayList<Question>()
                    response.body()!!.data.forEach { apiQuestion ->
                        questions.add(Question(text = apiQuestion.text))
                    }
                    return questions
                }
            }
            null
        } catch (e: Exception) {
            Timber.e(e)
            return null
        }
    }

    override fun getEvaluation(answers: String, answerCount: Int): Evaluation? {
        return try {
            val request = EvaluateRequest(answerCount, answers)
            val response = remoteDataSource.getEvaluation(request).execute()
            if (response.isSuccessful && response.body() != null) {
                if (response.body()!!.status == 200) {
                    return Evaluation(outcome = response.body()!!.data)
                }
            }
            null
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}