package com.iak.perstest.presentation.ui.pages.landing

import com.google.gson.Gson
import com.iak.perstest.R
import com.iak.perstest.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(private val gson: Gson) : BaseViewModel() {
    fun navigate() {
        navigate(R.id.actionQuiz)
    }
}