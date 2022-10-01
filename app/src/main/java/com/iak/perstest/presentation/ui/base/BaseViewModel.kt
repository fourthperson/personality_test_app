package com.iak.perstest.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    private val _openTaskEvent = MutableLiveData<Event<Int>>()
    val navigator: LiveData<Event<Int>> = _openTaskEvent

    fun navigate(routeId: Int) {
        _openTaskEvent.postValue(Event(routeId))
    }
}