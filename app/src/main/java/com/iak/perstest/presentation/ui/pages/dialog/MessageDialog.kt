package com.iak.perstest.presentation.ui.pages.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.iak.perstest.databinding.ConfirmDialogBinding

class MessageDialog : DialogFragment() {
    private var _binding: ConfirmDialogBinding? = null
    private val layout get() = _binding!!

    private lateinit var message: String
    private lateinit var positiveText: String
    private lateinit var negativeText: String
    private lateinit var sender: String

    data class MessageOutcome(val accepted: Boolean, val sender: String)

    companion object {
        const val Tag: String = "msg_dialog"

        private const val msgKey = "m"
        private const val posKey = "y"
        private const val negKey = "n"
        private const val sndKey = "s"

        fun instance(
            message: String,
            positiveText: String,
            negativeText: String,
            sender: String
        ): MessageDialog {
            val dialog = MessageDialog()
            val bundle = Bundle()
            bundle.putString(msgKey, message)
            bundle.putString(posKey, positiveText)
            bundle.putString(negKey, negativeText)
            bundle.putString(sndKey, sender)
            dialog.arguments = bundle
            return dialog
        }
    }

    private val viewModel: MessageDialogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = requireArguments()
        message = bundle.getString(msgKey)!!
        positiveText = bundle.getString(posKey)!!
        negativeText = bundle.getString(negKey)!!
        sender = bundle.getString(sndKey)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConfirmDialogBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog?.window != null) {
            requireDialog().window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun init() {
        layout.title.text = message
        layout.buttonYes.text = positiveText
        layout.buttonNo.text = negativeText

        layout.buttonYes.setOnClickListener {
            dismiss()
            viewModel.interact(true, sender)
        }

        layout.buttonNo.setOnClickListener {
            dismiss()
            viewModel.interact(false, sender)
        }
    }
}