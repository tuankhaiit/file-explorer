package com.tuankhaiit.fileexplorer.data.provider

import com.tuankhaiit.fileexplorer.model.FileModel
import com.tuankhaiit.fileexplorer.util.FileUtils
import java.io.File

class InternalStorageFileProvider : FileProvider() {
    override suspend fun retrieveFilesFromPath(path: String): List<FileModel> {
        val root = File(path)
        return FileUtils.getListFiles(root).filter {
            it.isHidden.not()
        }.map { mapFileToModel(it) }
    }

    override suspend fun findFileByPath(path: String): FileModel? {
        val file = File(path)
        if (file.exists()) {
            return mapFileToModel(file)
        }
        return null
    }

    private fun mapFileToModel(file: File): FileModel {
        val attrs = HashMap<String, Any>()
        if (file.isDirectory) {
            attrs[FileModel.CHILD_COUNT_ATTR] = file.list()?.size ?: 0
        } else {
            attrs[FileModel.FILE_SIZE_ATTR] = file.length()
        }
        return FileModel(file.name, file.path, file.lastModified(), file.isDirectory, attrs)
    }

}