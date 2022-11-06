package com.tuankhaiit.fileexplorer.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

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

}