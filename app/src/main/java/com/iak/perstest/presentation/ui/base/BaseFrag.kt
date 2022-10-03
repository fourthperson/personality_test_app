package com.iak.perstest.presentation.ui.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import timber.log.Timber

open class BaseFrag : Fragment() {
    fun showDialog(dialog: DialogFragment, tag: String) {
        try {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val prev = requireActivity().supportFragmentManager.findFragmentByTag(tag)
            if (prev != null) {
                transaction.remove(prev)
            }
            transaction.addToBackStack(null)
            dialog.show(transaction, tag)
        } catch (e: IllegalStateException) {
            Timber.e(e)
        }
    }
}