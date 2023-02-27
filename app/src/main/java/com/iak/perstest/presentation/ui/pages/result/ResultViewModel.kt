package com.iak.perstest.presentation.ui.pages.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iak.perstest.domain.entity.PastTest
import com.iak.perstest.domain.use_case.GetPastTestsUseCase
import com.iak.perstest.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(private val getPastTestsUseCase: GetPastTestsUseCase) :
    BaseViewModel() {

    private val _history = MutableLiveData<List<PastTest>>()

    val history: LiveData<List<PastTest>>
        get() = _history

    fun loadPastTestResults() {
        viewModelScope.launch(Dispatchers.IO) {
            getPastTestsUseCase.invoke().let { results -> _history.postValue(results) }
        }
    }

    fun parade(colours: List<Int>): List<Party> {
        val party = Party(
            speed = 10f,
            maxSpeed = 30f,
            damping = 0.9f,
            angle = Angle.RIGHT - 45,
            spread = Spread.SMALL,
            colors = colours,
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