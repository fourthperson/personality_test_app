package com.iak.perstest.presentation.ui.pages.dialog

import androidx.lifecycle.ViewModel
import org.greenrobot.eventbus.EventBus

class ConfirmDialogViewModel : ViewModel() {
    fun interact(answer: Boolean) {
        EventBus.getDefault().post(ConfirmDialog.ConfirmOutcome(answer))
    }
}