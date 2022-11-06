package com.tuankhaiit.fileexplorer.data.repository

import com.tuankhaiit.fileexplorer.data.provider.FileProvider
import com.tuankhaiit.fileexplorer.data.provider.InternalStorageFileProvider
import com.tuankhaiit.fileexplorer.model.FileModel
import com.tuankhaiit.fileexplorer.model.SortMode
import com.tuankhaiit.fileexplorer.model.SortType

class FileRepositoryImpl(
    private val provider: FileProvider = InternalStorageFileProvider()
) : FileRepository() {

    override suspend fun retrieveFilesFromPath(path: String, sortType: SortType, sortMode: SortMode): List<FileModel> {
        val files = provider.retrieveFilesFromPath(path)
        return FileModel.sortWithType(files, sortType, sortMode)
    }

    override suspend fun findFileByPath(path: String): FileModel? {
        return provider.findFileByPath(path)
    }
}