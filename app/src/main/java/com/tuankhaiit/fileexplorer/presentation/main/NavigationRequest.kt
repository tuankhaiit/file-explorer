package com.tuankhaiit.fileexplorer.presentation.main

import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType

data class NavigationRequest(
    val path: String,
    val sortType: SortType,
    val sortMode: SortMode
)