package com.tuankhaiit.fileexplorer.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.tuankhaiit.fileexplorer.R

abstract class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        loadData()
    }

    open fun initViews() {

    }

    open fun initObservers() {

    }

    open fun loadData() {

    }

    protected fun showAlertMessage(
        @StringRes messageResId: Int,
        callback: ((Boolean) -> Unit)? = null
    ) {
        showAlertMessage(getString(messageResId), callback)
    }

    protected fun showAlertMessage(message: String, callback: ((Boolean) -> Unit)? = null) {
        AlertDialog.Builder(requireContext(), R.style.Theme_FileExplorer_AlertDialog)
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                callback?.invoke(true) ?: dialog.dismiss()
            }
            .create().show()
    }

}