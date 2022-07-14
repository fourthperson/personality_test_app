package com.iak.perstest.ui.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.iak.perstest.R
import com.iak.perstest.adapter.QuizAdapter
import com.iak.perstest.base.App
import com.iak.perstest.databinding.FragQuizBinding
import com.iak.perstest.ui.result.ResultFrag
import com.iak.perstest.viewmodel.QuestionViewModel

class QuizFrag : Fragment() {
    private var _binding: FragQuizBinding? = null
    private val layout get() = _binding!!

    private lateinit var questions: List<String>

    private lateinit var answers: MutableList<Boolean>

    private lateinit var viewModel: QuestionViewModel

    private lateinit var _context: Context

    private lateinit var adapter: QuizAdapter

    private var position: Int = -1

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        layout.questionGroup.visibility = View.GONE

        layout.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        questions = viewModel.getQuestions()
        answers = MutableList(questions.size) { false }

        position = 0

        val callback = object : QuizAdapter.ClickListener {
            override fun onTrue() {
                answers[position] = true
                next()
            }

            override fun onFalse() {
                answers[position] = false
                next()
            }
        }

        val layoutManager =
            object : LinearLayoutManager(_context, HORIZONTAL, false) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }

                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

        adapter = QuizAdapter(questions, callback)

        layout.recycler.layoutManager = layoutManager
        layout.recycler.itemAnimator = DefaultItemAnimator()
        layout.recycler.adapter = adapter

        layout.recycler.scrollToPosition(position)
    }

    private fun next() {
        if (position < questions.size - 1) {
            position++
            layout.recycler.scrollToPosition(position)
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