package com.tuankhaiit.fileexplorer.data.repository

import com.tuankhaiit.fileexplorer.model.FileModel
import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType

abstract class FileRepository {
    abstract suspend fun retrieveFilesFromPath(
        path: String,
        sortType: SortType,
        sortMode: SortMode
    ): List<FileModel>

    abstract suspend fun findFileByPath(path: String): FileModel?
}