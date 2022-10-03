package com.iak.perstest.presentation.ui.pages.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iak.perstest.R
import com.iak.perstest.databinding.FragQuizBinding
import com.iak.perstest.presentation.ui.adapter.ViewPager2Adapter
import com.iak.perstest.presentation.ui.base.BaseFrag
import com.iak.perstest.presentation.ui.pages.dialog.ConfirmDialog
import com.iak.perstest.presentation.ui.pages.dialog.MessageDialog
import com.iak.perstest.presentation.ui.pages.result.ResultFrag
import com.iak.perstest.presentation.util.Status
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber

@AndroidEntryPoint
class QuizFrag : BaseFrag() {
    private var _binding: FragQuizBinding? = null
    private val layout get() = _binding!!

    private val viewModel: QuizFragViewModel by viewModels()

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
        EventBus.getDefault().register(this)
        init()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onMessageEvent(event: ConfirmDialog.ConfirmOutcome) {
        if (event.outcome) {
            viewModel.answer(true)
        } else {
            viewModel.answer(false)
        }
    }

    @Subscribe
    fun onMessageEvent(event: MessageDialog.MessageOutcome) {
        if (event.sender.startsWith("confirm_retry:")) {
            val retryQuestion = event.sender.split(":")[1].toBoolean()
            if (event.accepted) {
                if (retryQuestion) {
                    viewModel.getQuestions()
                } else {
                    viewModel.getEvaluation()
                }
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun init() {
        layout.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        layout.pager.isUserInputEnabled = false

        val adapter = ViewPager2Adapter(requireActivity())

        layout.pager.adapter = adapter

        viewModel.navigator.observe(viewLifecycleOwner) {
            
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
                    layout.loader.visibility = View.GONE
                    resource.data?.let { questions ->
                        questions.forEach { q ->
                            try {
                                val frag = QuestionFrag.instance(q)
                                adapter.addFragment(frag, "")
                            } catch (e: Exception) {
                                Timber.e(e)
                            }
                        }
                        layout.pager.adapter = adapter
                    }
                }

                Status.LOADING -> {
                    layout.loader.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    layout.loader.visibility = View.GONE
                    retryDialog(resource.message!!, true)
                }
            }
        }

        viewModel.assessment.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    layout.loader.visibility = View.GONE
                    resource.data?.let { outcome ->
                        nav(outcome)
                    }
                }

                Status.LOADING -> {
                    layout.loader.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    layout.loader.visibility = View.GONE
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
        val retryDialog =
            MessageDialog.instance(
                message,
                getString(R.string.label_retry),
                getString(R.string.label_cancel),
                "confirm_retry:$isQuestion"
            )

        showDialog(retryDialog, MessageDialog.Tag)
    }

    private fun nav(outcome: String) {
        val args = Bundle()
        args.putString(ResultFrag.Data, outcome)
        findNavController().popBackStack(R.id.quizFrag, true)
        findNavController().navigate(R.id.actionResult, args)
    }
}