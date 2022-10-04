package com.iak.perstest.data.data_source

import com.iak.perstest.data.entity.PastQuiz

interface LocalDataSource {
    fun getPastQuizzes(): List<PastQuiz>

    fun insert(quiz: PastQuiz)
}