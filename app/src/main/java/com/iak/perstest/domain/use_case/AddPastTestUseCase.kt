package com.iak.perstest.domain.use_case

import com.iak.perstest.domain.entity.PastTest
import com.iak.perstest.domain.repo.QuizRepo

class AddPastTestUseCase(private val quizRepo: QuizRepo) {
    operator fun invoke(test: PastTest) {
        quizRepo.add(test)
    }
}