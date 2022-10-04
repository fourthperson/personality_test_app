package com.iak.perstest.domain.repo

import com.iak.perstest.domain.entity.Evaluation
import com.iak.perstest.domain.entity.Question

interface TestRepo {
    fun getQuestions(): MutableList<Question>?

    fun getEvaluation(answers: String, answerCount: Int): Evaluation?
}