package com.tuankhaiit.fileexplorer.presentation.main

import androidx.lifecycle.MutableLiveData
import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType
import com.tuankhaiit.fileexplorer.presentation.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    // to communicate with address bar
    val currentPath = MutableLiveData<String>()
    val currentSortType = MutableLiveData<SortType>()
    val currentSortMode = MutableLiveData<SortMode>()
}