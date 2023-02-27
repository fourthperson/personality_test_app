package com.iak.perstest.presentation.ui.pages.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.iak.perstest.databinding.FragQuestionBinding
import com.iak.perstest.domain.entity.Question
import com.iak.perstest.presentation.ui.base.BaseFrag
import com.iak.perstest.presentation.ui.pages.dialog.ConfirmDialog

class QuestionFrag : BaseFrag() {
    private var _binding: FragQuestionBinding? = null
    private val layout get() = _binding!!

    private lateinit var question: Question

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragQuestionBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        layout.textQuestion.text = question.text
        layout.buttonTrue.setOnClickListener { confirm(true) }
        layout.buttonFalse.setOnClickListener { confirm(false) }
    }

    private fun confirm(answer: Boolean) {
        val dialog = ConfirmDialog.instance(answer)

        showDialog(dialog, ConfirmDialog.Tag)
    }
}