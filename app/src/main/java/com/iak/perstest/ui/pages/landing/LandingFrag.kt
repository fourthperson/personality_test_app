package com.iak.perstest.ui.pages.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iak.perstest.R
import com.iak.perstest.databinding.FragLandingBinding

class LandingFrag : Fragment() {
    private var _binding: FragLandingBinding? = null
    private val layout get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragLandingBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        layout.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.actionQuiz)
        }
    }
}