package com.iak.perstest.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iak.perstest.databinding.FragResultsBinding

class FragResult : Fragment() {
    private var _binding: FragResultsBinding? = null
    private val layout get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragResultsBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        layout.closeButton.setOnClickListener {

        }
        layout.closeButton.setOnClickListener {

        }
    }
}