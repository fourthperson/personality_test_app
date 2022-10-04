package com.iak.perstest.domain.use_case

import com.iak.perstest.domain.entity.PastTest
import com.iak.perstest.domain.repo.QuizRepo

class GetPastTestsUseCase(private val quizRepo: QuizRepo) {
    operator fun invoke(): List<PastTest> {
        return quizRepo.getQuizHistory()
    }
}