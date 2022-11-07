package com.tuankhaiit.fileexplorer.util

import java.io.File

object FileUtils {

    fun getListFiles(dir: File): List<File> {
        if (dir.isDirectory) {
            return dir.listFiles()?.toList() ?: emptyList()
        }
        return emptyList()
    }

    fun getParentFromPath(path: String): String {
        val endIndex = path.length - 1
        val lastSeparatorIndex = path.lastIndexOf(File.separator)
        return path.removeRange(lastSeparatorIndex..endIndex)
    }
}