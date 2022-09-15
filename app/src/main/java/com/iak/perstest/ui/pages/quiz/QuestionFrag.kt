package com.iak.perstest.ui.pages.quiz

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.iak.perstest.data.entity.Question
import com.iak.perstest.databinding.FragQuestionBinding
import timber.log.Timber

class QuestionFrag : Fragment() {
    private var _binding: FragQuestionBinding? = null
    private val layout get() = _binding!!

    private lateinit var question: Question

    private var quizCallback: QuizFrag.QuizCallback? = null

    private val confirmCallback = object : ConfirmDialog.ConfirmCallback {
        override fun onConfirm(answer: Boolean, dialog: DialogInterface?) {
            if (answer) {
                quizCallback?.onTrue()
            } else {
                quizCallback?.onFalse()
            }
        }

        override fun onCancel(dialog: DialogInterface?) {
        }
    }

    companion object {
        private const val dataKey: String = "data"

        fun instance(question: Question): QuestionFrag {
            val frag = QuestionFrag()
            val args = Bundle()
            args.putString(dataKey, Gson().toJson(question))
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = requireArguments().getString(dataKey)!!
        question = Gson().fromJson(data, Question::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragQuestionBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    fun setCallback(callback: QuizFrag.QuizCallback) {
        quizCallback = callback
    }

    private fun init() {
        layout.textQuestion.text = question.text
        layout.buttonTrue.setOnClickListener { confirm(true) }
        layout.buttonFalse.setOnClickListener { confirm(false) }
    }

    private fun confirm(answer: Boolean) {
        val dialog = ConfirmDialog.instance(answer)
        dialog.setCallback(confirmCallback)

        try {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val prev = requireActivity().supportFragmentManager.findFragmentByTag(ConfirmDialog.Tag)
            if (prev != null) {
                transaction.remove(prev)
            }
            transaction.addToBackStack(null)
            dialog.show(transaction, ConfirmDialog.Tag)
        } catch (e: IllegalStateException) {
            Timber.e(e)
        }
    }
}