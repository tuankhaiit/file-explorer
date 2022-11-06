package com.tuankhaiit.fileexplorer.presentation.main

import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType

interface FileExplorerNavigation {
    fun navigateToPath(
        path: String,
        sortType: SortType,
        sortMode: SortMode
    )

    fun backToParent()
}