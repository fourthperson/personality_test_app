package com.iak.perstest.presentation.ui.pages.quiz

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iak.perstest.R
import com.iak.perstest.domain.entity.PastTest
import com.iak.perstest.domain.entity.Question
import com.iak.perstest.domain.use_case.AddPastTestUseCase
import com.iak.perstest.domain.use_case.GetEvaluationUseCase
import com.iak.perstest.domain.use_case.GetQuestionsUseCase
import com.iak.perstest.presentation.ui.base.BaseViewModel
import com.iak.perstest.presentation.ui.pages.dialog.MessageDialog
import com.iak.perstest.presentation.ui.pages.result.ResultFrag
import com.iak.perstest.presentation.util.ErrorType
import com.iak.perstest.presentation.util.NetworkStatus
import com.iak.perstest.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val getEvaluationUseCase: GetEvaluationUseCase,
    private val addPastTestUseCase: AddPastTestUseCase,
    private val networkHelper: NetworkStatus,
) : BaseViewModel() {
    val questionsPerQuiz = 5

    private val _questions = MutableLiveData<Resource<MutableList<Question>>>()
    private val _assessment = MutableLiveData<Resource<Any>>()
    private var _currentPosition = MutableLiveData<Int>()
    private var _quizCompleted = MutableLiveData<Boolean>()
    private var _answers = MutableList(questionsPerQuiz) { false }

    val questions: LiveData<Resource<MutableList<Question>>>
        get() = _questions

    val assessment: MutableLiveData<Resource<Any>>
        get() = _assessment

    val currentPosition: LiveData<Int>
        get() = _currentPosition

    val quizCompleted: LiveData<Boolean>
        get() = _quizCompleted

    init {
        getQuestions()
    }

    private fun getQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            _questions.postValue(Resource.loading(null))

            if (networkHelper.connected()) {
                getQuestionsUseCase.invoke().let { result ->
                    if (result != null) {
                        result.shuffle()
                        _questions.postValue(Resource.success(result.subList(0, questionsPerQuiz)))
                        _currentPosition.postValue(0)
                        _quizCompleted.postValue(false)
                    } else {
                        _questions.postValue(Resource.error(ErrorType.FAILURE, null))
                    }
                }
            } else {
                _questions.postValue(Resource.error(ErrorType.NO_CONNECTION, null))
            }
        }
    }

    fun getEvaluation() {
        viewModelScope.launch(Dispatchers.IO) {
            _assessment.postValue(Resource.loading(null))

            if (networkHelper.connected()) {
                getEvaluationUseCase.invoke(answerString(), _answers.size).let { result ->
                    if (result != null) {
                        // save completed outcome to local db
                        val pastTest = PastTest(result.outcome, timeString())
                        addPastTestUseCase.invoke(pastTest)
                        // post results to ui
                        _assessment.postValue(Resource.success(result.outcome))
                    } else {
                        _assessment.postValue(Resource.error(ErrorType.FAILURE, null))
                    }
                }
            } else {
                _questions.postValue(Resource.error(ErrorType.NO_CONNECTION, null))
            }
        }
    }

    fun answer(answer: Boolean) {
        val currentPos = currentPosition.value!!
        _answers[currentPos] = answer
        if (currentPos < questionsPerQuiz - 1) {
            // move to next position
            _currentPosition.postValue(currentPos + 1)
        } else {
            // quiz complete
            _currentPosition.postValue(currentPos + 1)
            _quizCompleted.postValue(true)
        }
    }

    fun handleRetry(event: MessageDialog.MessageOutcome) {
        val retryQuestion = event.sender.split(":")[1].toBoolean()
        if (event.accepted) {
            if (retryQuestion) {
                getQuestions()
            } else {
                getEvaluation()
            }
        } else {
            _navController?.popBackStack()
        }
    }

    fun results(outcome: String) {
        val args = Bundle()
        args.putString(ResultFrag.Data, outcome)
        _navController?.popBackStack(R.id.quizFrag, true)
        _navController?.navigate(R.id.actionResult, args)
    }

    private fun answerString(): String {
        var answersStr = ""
        for (i in _answers.indices) {
            answersStr += _answers[i].toString() + if (i == _answers.size - 1) "" else ";"
        }
        Timber.i(answersStr)
        return answersStr
    }

    private fun timeString(): String {
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(Date())
    }
}