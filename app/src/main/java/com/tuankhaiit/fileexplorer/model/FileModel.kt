package com.tuankhaiit.fileexplorer.model

import com.tuankhaiit.fileexplorer.model.comparator.FileModelSortByNameComparator

data class FileModel(
    val name: String,
    val path: String,
    val createdDate: Long,
    val isDirectory: Boolean = false,
    val attributes: HashMap<String, Any>? = null
) {
    fun length(): Long = attributes?.get(FILE_SIZE_ATTR)?.toString()?.toLongOrNull() ?: 0

    fun childCount(): Int = attributes?.get(CHILD_COUNT_ATTR)?.toString()?.toIntOrNull() ?: 0

    companion object {
        const val FILE_SIZE_ATTR = "FILE_SIZE_ATTR"
        const val CHILD_COUNT_ATTR = "CHILD_COUNT_ATTR"

        fun sortWithType(input: List<FileModel>, sortType: SortType, sortMode: SortMode) : List<FileModel> {
            val comparator = when(sortType) {
                else -> FileModelSortByNameComparator(sortMode)
            }
            return input.sortedWith(comparator)
        }
    }
}
