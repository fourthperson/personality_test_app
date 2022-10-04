package com.iak.perstest.presentation.ui.pages.landing

import com.iak.perstest.R
import com.iak.perstest.presentation.ui.base.BaseViewModel

class LandingViewModel : BaseViewModel() {
    fun startQuiz() {
        _navController?.navigate(R.id.actionQuiz)
    }
}