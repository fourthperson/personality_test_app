package com.iak.perstest.presentation.ui.pages.splash

import com.iak.perstest.R
import com.iak.perstest.presentation.ui.base.BaseViewModel

class SplashViewModel : BaseViewModel() {
    fun landingPage() {
        navController?.popBackStack(R.id.splashFrag, true)
        navController?.navigate(R.id.actionLanding)
    }
}