package com.iak.perstest.domain.repo

import com.iak.perstest.domain.entity.PastTest

interface QuizRepo {
    fun getQuizHistory(): List<PastTest>

    fun add(test: PastTest)
}