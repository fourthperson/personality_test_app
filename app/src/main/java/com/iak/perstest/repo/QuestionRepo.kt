package com.iak.perstest.repo

import com.iak.perstest.resources.Questions

class QuestionRepo {
    companion object {
        var instance = QuestionRepo()
    }

    fun getQuestions(count: Int): List<String> {
        val questions = Questions.questions.subList(0, count - 1);
        return questions;
    }

    fun assessAnswers(questions: List<String>, answers: List<Boolean>): String {
        var trueCount = 0;
        var falseCount = 0;
        for (answer in answers) {
            if (answer) {
                trueCount++;
            } else {
                falseCount++;
            }
        }
        val verdict: String = if (trueCount > falseCount) {
            "Introverted";
        } else if (falseCount > trueCount) {
            "Extroverted";
        } else {
            "BalancedL";
        }
        return verdict;
    }
}