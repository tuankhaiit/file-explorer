package com.tuankhaiit.fileexplorer.model.comparator

import com.tuankhaiit.fileexplorer.model.FileModel
import com.tuankhaiit.fileexplorer.model.SortMode

class FileModelSortByNameComparator(private val mode: SortMode) : java.util.Comparator<FileModel> {
    override fun compare(p0: FileModel, p1: FileModel): Int {
        if (p0.isDirectory && p1.isDirectory.not()) return -1
        if (p0.isDirectory.not() && p1.isDirectory) return 1
        return if (mode == SortMode.ASCENDING) {
            runCompare(p0, p1)
        } else {
            runCompare(p1, p0)
        }
    }

    private fun runCompare(p0: FileModel, p1: FileModel) : Int {
        return p0.name.compareTo(p1.name)
    }
}