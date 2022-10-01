package com.iak.perstest.presentation.ui.pages.result

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iak.perstest.R
import com.iak.perstest.databinding.FragResultsBinding
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ResultFrag : Fragment() {
    private var _binding: FragResultsBinding? = null
    private val layout get() = _binding!!

    private lateinit var result: String

    private lateinit var _context: Context

    companion object {
        const val Data: String = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        result = requireArguments().getString(Data).toString()
        Timber.i(result)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragResultsBinding.inflate(inflater, container, false)
        return layout.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        layout.closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
        layout.buttonClose.setOnClickListener {
            findNavController().popBackStack()
        }
        layout.textQuestion.text =
            String.format("%s\n\n%s", getString(R.string.result_template), result)
        layout.konfetti.start(parade())
    }

    private fun parade(): List<Party> {
        val party = Party(
            speed = 10f,
            maxSpeed = 30f,
            damping = 0.9f,
            angle = Angle.RIGHT - 45,
            spread = Spread.SMALL,
            colors = listOf(
                ContextCompat.getColor(_context, R.color.white),
                ContextCompat.getColor(_context, R.color.secondarylight),
                ContextCompat.getColor(_context, R.color.light),
            ),
            shapes = listOf(Shape.Circle, Shape.Square),
            emitter = Emitter(duration = 2500, TimeUnit.MILLISECONDS).perSecond(10),
            position = Position.Relative(0.0, 0.5),
            timeToLive = 2500,
            size = listOf(
                Size(12)
            ),
        )

        return listOf(
            party,
            party.copy(
                angle = party.angle - 90, // flip angle from right to left
                position = Position.Relative(1.0, 0.5)
            ),
        )
    }
}