package com.iak.perstest.domain.use_case

import com.iak.perstest.domain.entity.Evaluation
import com.iak.perstest.domain.repo.TestRepo
import javax.inject.Inject

class GetEvaluationUseCase @Inject constructor(private val repository: TestRepo) {
    operator fun invoke(answers: String, answerCount: Int): Evaluation? {
        return repository.getEvaluation(answers, answerCount)
    }
}