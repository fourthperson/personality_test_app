package com.iak.perstest.ui.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.iak.perstest.R
import com.iak.perstest.adapter.QuizAdapter
import com.iak.perstest.base.App
import com.iak.perstest.databinding.FragQuizBinding
import com.iak.perstest.viewmodel.QuestionViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import timber.log.Timber

class QuizFrag : Fragment() {
    private var _binding: FragQuizBinding? = null
    private val layout get() = _binding!!

    private lateinit var questions: List<String>

    private lateinit var answers: MutableList<Boolean>

    private lateinit var viewModel: QuestionViewModel

    private lateinit var adapter: QuizAdapter

    private lateinit var _context: Context

    private var position: Int = 0

    private var questionCount = 1;

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
        layout.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        questions = viewModel.getQuestions()
        answers = MutableList(questions.size) { false }

        val callback = object : QuizAdapter.ClickListener {
            override fun onTrue() {
                nextQuestion(true)
            }

            override fun onFalse() {
                nextQuestion(false)
            }
        }

        adapter = QuizAdapter(questions, callback)
        layout.recycler.layoutManager = object :
            LinearLayoutManager(_context, HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        layout.recycler.itemAnimator = SlideInUpAnimator()
        layout.recycler.adapter = adapter
    }

    private fun nextQuestion(answer: Boolean) {
        questionCount++;
        if (questionCount == questions.size) {
            // show results screen
            val outcome = viewModel.getAssessment(answers)
            val args = Bundle()
            args.putString("data", outcome)
            findNavController().popBackStack(R.id.quizFrag, true)
            findNavController().navigate(R.id.actionResult, args)
            return;
        }
        if (position < questions.size) {
            answers[position] = answer
            position++
            layout.recycler.scrollToPosition(position)
            val percentage = (position.toDouble() / questions.size) * 100.0
            layout.progressBar.setProgressPercentage(percentage)
        }
    }
}