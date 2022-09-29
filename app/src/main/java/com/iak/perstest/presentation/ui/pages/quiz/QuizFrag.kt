package com.iak.perstest.presentation.ui.pages.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iak.perstest.R
import com.iak.perstest.databinding.CofnirmDialogBinding
import com.iak.perstest.databinding.FragQuizBinding
import com.iak.perstest.presentation.ui.adapter.ViewPager2Adapter
import com.iak.perstest.presentation.util.Status
import com.iak.perstest.ui.pages.result.ResultFrag
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFrag : Fragment() {
    private var _binding: FragQuizBinding? = null
    private val layout get() = _binding!!

    private lateinit var adapter: ViewPager2Adapter

    private val viewModel: QuizViewModel by viewModels()

    interface QuizCallback {
        fun onTrue()
        fun onFalse()
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

        adapter = ViewPager2Adapter(requireActivity())

        val callback = object : QuizCallback {
            override fun onTrue() {
                viewModel.answer(true)
            }

            override fun onFalse() {
                viewModel.answer(false)
            }
        }

        viewModel.quizCompleted.observe(viewLifecycleOwner) { completed ->
            if (completed) {
                completeQuiz()
            }
        }

        viewModel.currentPosition.observe(viewLifecycleOwner) { position ->
            val perc = (position.toDouble() / viewModel.questionsPerQuiz) * 100
            layout.progressBar.setProgressPercentage(perc)
            layout.pager.setCurrentItem(position, true)
        }

        viewModel.questions.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
//                    layout.loader.visibility = View.GONE
                    resource.data?.let { questions ->
                        questions.forEach { q ->
                            val frag = QuestionFrag.instance(q)
                            frag.setCallback(callback)
                            adapter.addFragment(frag, "")
                        }
                        layout.pager.adapter = adapter
                    }
                }

                Status.LOADING -> {
//                    layout.loader.visibility = View.VISIBLE
                }

                Status.ERROR -> {
//                    layout.loader.visibility = View.GONE
                    retryDialog(resource.message!!, true)
                }
            }
        }

        viewModel.assessment.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
//                    layout.loader.visibility = View.GONE
                    resource.data?.let { outcome ->
                        nav(outcome)
                    }
                }

                Status.LOADING -> {
//                    layout.loader.visibility = View.VISIBLE
                }

                Status.ERROR -> {
//                    layout.loader.visibility = View.GONE
                    retryDialog(resource.message!!, false)
                }
            }
        }
    }

    private fun completeQuiz() {
        viewModel.questions.removeObservers(viewLifecycleOwner)
        viewModel.getEvaluation()
    }

    private fun retryDialog(message: String, isQuestion: Boolean) {
        val dialogView = CofnirmDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(dialogView.root)

        val dialog = builder.create()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.show()

        dialogView.title.text = message
        dialogView.buttonYes.text = getString(R.string.label_retry)
        dialogView.buttonNo.text = getString(R.string.label_cancel)

        dialogView.buttonYes.setOnClickListener {
            if (isQuestion) {
                viewModel.getQuestions()
            } else {
                viewModel.getEvaluation()
            }
        }
        dialogView.buttonNo.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    private fun nav(outcome: String) {
        val args = Bundle()
        args.putString(ResultFrag.Data, outcome)
        findNavController().popBackStack(R.id.quizFrag, true)
        findNavController().navigate(R.id.actionResult, args)
    }
}