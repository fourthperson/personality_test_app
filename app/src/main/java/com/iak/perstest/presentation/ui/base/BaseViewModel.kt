package com.iak.perstest.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

open class BaseViewModel : ViewModel() {
    var _navController: NavController? = null

    fun setNavController(controller: NavController) {
        _navController = controller
    }

    fun navBack() {
        _navController?.popBackStack()
    }
}