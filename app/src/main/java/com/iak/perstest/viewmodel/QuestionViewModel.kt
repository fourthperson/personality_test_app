package com.iak.perstest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.iak.perstest.repo.QuestionRepo

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private var repo = QuestionRepo.instance

    fun getQuestions(): List<String> {
        return repo.getQuestions(6)
    }

    fun getAssessment(answers: List<Boolean>): String {
        return repo.assessAnswers(answers)
    }
}