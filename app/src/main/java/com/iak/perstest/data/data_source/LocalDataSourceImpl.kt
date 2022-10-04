package com.iak.perstest.data.data_source

import com.iak.perstest.data.data_source.db.PastQuizDao
import com.iak.perstest.data.entity.PastQuiz
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val pastQuizDao: PastQuizDao) :
    LocalDataSource {
    override fun getPastQuizzes(): List<PastQuiz> {
        return pastQuizDao.getAll()
    }

    override fun insert(quiz: PastQuiz) {
        pastQuizDao.insert(quiz)
    }
}