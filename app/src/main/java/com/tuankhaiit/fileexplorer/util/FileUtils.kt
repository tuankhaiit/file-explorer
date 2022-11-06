package com.tuankhaiit.fileexplorer.util

import android.os.Environment
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

    fun isExternalStorageReadOnly(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }

    fun isExternalStorageAvailable(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }
}