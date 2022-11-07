package com.tuankhaiit.fileexplorer.presentation.main

interface FileExplorerNavigation {
    fun navigateToPath(navigationRequest: NavigationRequest)

    fun backToParent()
}