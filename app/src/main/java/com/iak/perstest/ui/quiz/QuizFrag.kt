package com.iak.perstest.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iak.perstest.base.App
import com.iak.perstest.databinding.FragQuizBinding
import com.iak.perstest.viewmodel.QuestionViewModel

class QuizFrag : Fragment() {
    private var _binding: FragQuizBinding? = null
    private val layout get() = _binding!!

    private lateinit var questions: List<String>
    private lateinit var answers: MutableList<Boolean>

    private lateinit var viewModel: QuestionViewModel

    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = App.vmFactory.create(QuestionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragQuizBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        questions = viewModel.getQuestions()
        answers = ArrayList(questions.size)
//        layout.toolbar.navigat
        layout.buttonTrue.setOnClickListener {
            answers[position] = true
            position++
            loadQuestion()
        }
        layout.buttonFalse.setOnClickListener {
            answers[position] = false
            position++
            loadQuestion()
        }
        loadQuestion()
    }

    private fun loadQuestion() {
        if (position < questions.size) {
            layout.textQuestion.text = questions[position]
        } else {
            // quiz finished
            // mark
            var assessment = viewModel.getAssessment(answers)
        }
    }
}