package com.iak.perstest.presentation.ui.pages.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.iak.perstest.R
import com.iak.perstest.databinding.FragResultsBinding
import com.iak.perstest.presentation.ui.adapter.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFrag : Fragment() {
    private var _binding: FragResultsBinding? = null
    private val layout get() = _binding!!

    private lateinit var result: String

    private val viewModel: ResultViewModel by viewModels()

    companion object {
        const val Data: String = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        result = requireArguments().getString(Data).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragResultsBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadPastTestResults()
    }

    private fun init() {
        viewModel.navController = findNavController()

        layout.closeButton.setOnClickListener {
            viewModel.navBack()
        }

        layout.buttonClose.setOnClickListener {
            viewModel.navBack()
        }

        layout.recycler.layoutManager = LinearLayoutManager(requireContext())
        layout.recycler.itemAnimator = DefaultItemAnimator()
        layout.recycler.adapter = HistoryAdapter(ArrayList())

        viewModel.history.observe(viewLifecycleOwner) {
            layout.recycler.adapter = HistoryAdapter(it)
        }

        layout.textQuestion.text = result

        layout.konfetti.start(
            viewModel.parade(
                listOf(
                    ContextCompat.getColor(requireContext(), R.color.white),
                    ContextCompat.getColor(requireContext(), R.color.secondaryLight),
                    ContextCompat.getColor(requireContext(), R.color.light),
                )
            )
        )
    }
}