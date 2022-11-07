package com.tuankhaiit.fileexplorer.presentation.main

import androidx.lifecycle.MutableLiveData
import com.tuankhaiit.fileexplorer.presentation.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    // to communicate with address bar
    val currentNavigationState = MutableLiveData<NavigationRequest>()
}