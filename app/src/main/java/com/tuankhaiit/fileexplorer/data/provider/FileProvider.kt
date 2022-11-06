package com.tuankhaiit.fileexplorer.data.provider

import com.tuankhaiit.fileexplorer.model.FileModel

abstract class FileProvider {
    abstract suspend fun retrieveFilesFromPath(path: String): List<FileModel>
    abstract suspend fun findFileByPath(path: String) : FileModel?
}