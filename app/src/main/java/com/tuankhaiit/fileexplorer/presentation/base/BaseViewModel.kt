package com.tuankhaiit.fileexplorer.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers

abstract class BaseViewModel : ViewModel() {
    fun dispatcherIO() = Dispatchers.IO
}