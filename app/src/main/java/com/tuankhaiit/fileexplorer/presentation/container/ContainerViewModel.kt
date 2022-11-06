package com.tuankhaiit.fileexplorer.presentation.container

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.tuankhaiit.fileexplorer.data.repository.FileRepositoryImpl
import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType
import com.tuankhaiit.fileexplorer.presentation.base.BaseViewModel
import com.tuankhaiit.fileexplorer.util.SingleLiveEvent
import kotlinx.coroutines.withContext

class ContainerViewModel : BaseViewModel() {
    private val repository by lazy { FileRepositoryImpl() }

    val listData = MutableLiveData<List<FileUI>>()

    val navigateToPathEvent = SingleLiveEvent<String>()

    suspend fun loadFiles(context: Context, path: String, sortType: SortType, sortMode: SortMode) {
        val files = withContext(dispatcherIO()) {
            repository.retrieveFilesFromPath(path, sortType, sortMode)
                .map { FileUI.from(context, it) }
        }
        listData.value = files
    }

    suspend fun onFileClick(fileUI: FileUI) {
        val file = withContext(dispatcherIO()) {
            repository.findFileByPath(fileUI.id)
        }
        if (file?.isDirectory == true) {
            navigateToPathEvent.value = file.path
        }
    }
}