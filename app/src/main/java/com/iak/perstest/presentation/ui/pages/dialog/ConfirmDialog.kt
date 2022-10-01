package com.iak.perstest.presentation.ui.pages.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.iak.perstest.databinding.CofnirmDialogBinding

class ConfirmDialog : DialogFragment() {
    private var _binding: CofnirmDialogBinding? = null
    private val layout get() = _binding!!

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

    interface ConfirmCallback {
        fun onConfirm(answer: Boolean, dialog: DialogInterface?)
        fun onCancel(dialog: DialogInterface?)
    }

    private var answer: Boolean? = null

    private var callback: ConfirmCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        answer = requireArguments().getBoolean(dataKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = CofnirmDialogBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog?.window != null) {
            requireDialog().window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        init()
    }

    fun setCallback(callback: ConfirmCallback) {
        this.callback = callback
    }

    private fun init() {
        layout.buttonYes.setOnClickListener {
            dismiss()
            callback?.onConfirm(answer!!, dialog)
        }
        layout.buttonNo.setOnClickListener {
            dismiss()
            callback?.onCancel(dialog)
        }
    }
}