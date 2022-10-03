package com.iak.perstest.presentation.ui.pages.dialog

import androidx.lifecycle.ViewModel
import org.greenrobot.eventbus.EventBus

class MessageDialogViewModel : ViewModel() {
    fun interact(outcome: Boolean, sender: String) {
        EventBus.getDefault().post(MessageDialog.MessageOutcome(outcome, sender))
    }
}