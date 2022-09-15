package com.iak.perstest.ui.pages.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iak.perstest.R
import com.iak.perstest.ui.adapter.ViewPager2Adapter
import com.iak.perstest.App
import com.iak.perstest.data.entity.Question
import com.iak.perstest.databinding.FragQuizBinding
import com.iak.perstest.ui.pages.result.ResultFrag
import com.iak.perstest.viewmodel.QuestionViewModel

class QuizFrag : Fragment() {
    private var _binding: FragQuizBinding? = null
    private val layout get() = _binding!!

    private lateinit var questions: List<String>

    private lateinit var answers: MutableList<Boolean>

    private lateinit var viewModel: QuestionViewModel

    private lateinit var adapter: ViewPager2Adapter

    private var position: Int = -1

    interface QuizCallback {
        fun onTrue()
        fun onFalse()
    }

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
        layout.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        layout.pager.isUserInputEnabled = false

        questions = viewModel.getQuestions()
        answers = MutableList(questions.size) { false }

        val callback = object : QuizCallback {
            override fun onTrue() {
                answer(true)
            }

            override fun onFalse() {
                answer(false)
            }
        }

        adapter = ViewPager2Adapter(requireActivity())

        questions.forEach { q ->
            val frag = QuestionFrag.instance(Question(q))
            frag.setCallback(callback)
            adapter.addFragment(frag, "")
        }

        layout.pager.adapter = adapter

        position = 0
    }

    private fun answer(answer: Boolean) {
        answers[position] = answer
        next()
    }

    private fun next() {
        if (position < questions.size - 1) {
            position++
            layout.pager.setCurrentItem(position, true)
            val percentage = (position.toDouble() / questions.size) * 100.0
            layout.progressBar.setProgressPercentage(percentage)
        } else {
            completeQuiz()
        }
    }

    private fun completeQuiz() {
        val outcome = viewModel.getAssessment(answers)
        val args = Bundle()
        args.putString(ResultFrag.Data, outcome)
        findNavController().popBackStack(R.id.quizFrag, true)
        findNavController().navigate(R.id.actionResult, args)
    }
}