package com.iak.perstest.domain.use_case

import com.iak.perstest.domain.entity.Question
import com.iak.perstest.domain.repo.TestRepo
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(private val questionRepo: TestRepo) {
    operator fun invoke(): MutableList<Question>? {
        return questionRepo.getQuestions()
    }
}