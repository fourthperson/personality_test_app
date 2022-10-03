package com.iak.perstest.presentation.ui.pages.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.iak.perstest.domain.GetEvaluationUseCase
import com.iak.perstest.domain.GetQuestionsUseCase
import com.iak.perstest.presentation.entity.Question
import com.iak.perstest.presentation.ui.base.BaseViewModel
import com.iak.perstest.presentation.util.NetworkStatus
import com.iak.perstest.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuizFragViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val getEvaluationUseCase: GetEvaluationUseCase,
    private val networkHelper: NetworkStatus,
    private val gson: Gson
) :
    BaseViewModel() {
    val questionsPerQuiz = 5

    private val _questions = MutableLiveData<Resource<MutableList<Question>>>()
    private val _assessment = MutableLiveData<Resource<String>>()
    private var _answers = MutableList(questionsPerQuiz) { false }
    private var _currentPosition = MutableLiveData<Int>()
    private var _quizCompleted = MutableLiveData<Boolean>()

    val questions: LiveData<Resource<MutableList<Question>>>
        get() = _questions

    val assessment: MutableLiveData<Resource<String>>
        get() = _assessment

    val currentPosition: LiveData<Int>
        get() = _currentPosition

    val quizCompleted: LiveData<Boolean>
        get() = _quizCompleted

    init {
        getQuestions()
    }

    fun getQuestions() {
        viewModelScope.launch {
            _questions.postValue(Resource.loading(null))

            if (networkHelper.connected()) {
                getQuestionsUseCase.invoke().let { response ->
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val jsonObject = response.body()!!

                            if (jsonObject.get("status").asInt == 200) {
                                val jsonArray = gson.toJson(jsonObject.getAsJsonArray("data"))
                                var list =
                                    gson.fromJson<MutableList<Question>>(
                                        jsonArray,
                                        object : TypeToken<MutableList<Question>>() {}.type
                                    )
                                list.shuffle()
                                list = list.subList(0, questionsPerQuiz)
                                _questions.postValue(Resource.success(list))
                                _currentPosition.postValue(0)
                                _quizCompleted.postValue(false)
                            } else {
                                val error = jsonObject.get("data").asString
                                _questions.postValue(Resource.error(error, null))
                            }
                        } else {
                            _questions.postValue(Resource.error("Null Data Was Received", null))
                        }
                    } else {
                        _questions.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                }
            } else {
                _questions.postValue(Resource.error("There is no internet connection", null))
            }
        }
    }

    fun getEvaluation() {
        viewModelScope.launch {
            _assessment.postValue(Resource.loading(null))

            if (networkHelper.connected()) {
                var answersStr = ""
                for (i in _answers.indices) {
                    answersStr += _answers[i].toString() + if (i == _answers.size - 1) "" else ";"
                }
                Timber.tag("MainViewModel").i("getEvaluation: %s", answersStr)

                val data = JsonObject()
                data.addProperty("answer_count", _answers.size)
                data.addProperty("answers", answersStr)

                getEvaluationUseCase.invoke(data).let { response ->
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val jsonObject = response.body()!!
                            if (jsonObject.get("status").asInt == 200) {
                                _assessment.postValue(Resource.success(jsonObject.get("data").asString))
                            } else {
                                _questions.postValue(
                                    Resource.error(
                                        jsonObject.get("data").asString,
                                        null
                                    )
                                )
                            }
                        } else {
                            _assessment.postValue(
                                Resource.error(
                                    "Received null data in response body",
                                    null
                                )
                            )
                        }
                    } else {
                        _assessment.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                }
            } else {
                _assessment.postValue(Resource.error("No internet connection", null))
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
}