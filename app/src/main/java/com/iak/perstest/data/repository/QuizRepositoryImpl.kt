package com.iak.perstest.data.repository

import com.iak.perstest.data.data_source.LocalDataSource
import com.iak.perstest.data.entity.PastQuiz
import com.iak.perstest.domain.entity.PastTest
import com.iak.perstest.domain.repo.QuizRepo

class QuizRepositoryImpl(private val localDataSource: LocalDataSource) : QuizRepo {
    override fun getQuizHistory(): List<PastTest> {
        val list = ArrayList<PastTest>();
        localDataSource.getPastQuizzes().forEach { pastQuiz ->
            list.add(PastTest(outcome = pastQuiz.outcome, date = pastQuiz.date))
        }
        return list
    }

    override fun add(test: PastTest) {
        localDataSource.insert(PastQuiz(id = 0, outcome = test.outcome, date = test.date))
    }
}