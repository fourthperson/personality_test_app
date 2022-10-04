package com.iak.perstest.presentation.ui.pages.splash

import com.iak.perstest.R
import com.iak.perstest.presentation.ui.base.BaseViewModel

class SplashViewModel : BaseViewModel() {
    fun landingPage() {
        _navController?.popBackStack(R.id.splashFrag, true)
        _navController?.navigate(R.id.actionLanding)
    }
}