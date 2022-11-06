package com.tuankhaiit.fileexplorer.presentation.container

import android.content.Context
import android.text.format.Formatter
import androidx.annotation.DrawableRes
import com.tuankhaiit.fileexplorer.R
import com.tuankhaiit.fileexplorer.model.FileModel
import java.text.SimpleDateFormat
import java.util.*

data class FileUI(
    val id: String,
    val name: String,
    val date: String,
    val info: String,
    @DrawableRes val icon: Int
) {
    companion object {
        fun from(context: Context, model: FileModel): FileUI {
            val info = if (model.isDirectory) {
                String.format(
                    context.resources.getQuantityString(
                        R.plurals.folder_child_item_count,
                        model.childCount(),
                        model.childCount()
                    )
                )
            } else {
                Formatter.formatFileSize(context, model.length())
            }

            val df = SimpleDateFormat("MMM d, yyyy HH:mm", Locale.getDefault())
            val createdDate = df.format(Date(model.createdDate))

            val icon = if (model.isDirectory)
                R.drawable.ic_baseline_folder_open
            else
                R.drawable.ic_baseline_normal_file

            return FileUI(model.path, model.name, createdDate, info, icon)
        }
    }
}