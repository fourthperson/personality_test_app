package com.iak.perstest.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

open class BaseViewModel : ViewModel() {
    var navController: NavController? = null

    fun navBack() {
        navController?.popBackStack()
    }
}