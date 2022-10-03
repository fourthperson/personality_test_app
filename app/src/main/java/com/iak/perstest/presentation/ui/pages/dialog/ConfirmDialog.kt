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

class ConfirmDialog : DialogFragment() {
    private var _binding: ConfirmDialogBinding? = null
    private val layout get() = _binding!!

    data class ConfirmOutcome(val outcome: Boolean)

    companion object {
        const val Tag: String = "confirm_dialog"
        const val dataKey: String = "data"

        fun instance(answer: Boolean): ConfirmDialog {
            val frag = ConfirmDialog()
            val args = Bundle()
            args.putBoolean(dataKey, answer)
            frag.arguments = args
            return frag
        }
    }

    private val viewModel: ConfirmDialogViewModel by viewModels()

    private var answer: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        answer = requireArguments().getBoolean(dataKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConfirmDialogBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog?.window != null) {
            requireDialog().window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        init()
    }

    private fun init() {
        layout.buttonYes.setOnClickListener {
            dismiss()
            viewModel.interact(answer!!)
        }
        layout.buttonNo.setOnClickListener {
            dismiss()
        }
    }
}