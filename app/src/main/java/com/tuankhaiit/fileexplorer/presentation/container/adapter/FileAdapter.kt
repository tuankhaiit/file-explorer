package com.tuankhaiit.fileexplorer.presentation.container.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tuankhaiit.fileexplorer.databinding.ItemFileLayoutBinding
import com.tuankhaiit.fileexplorer.presentation.container.FileUI

class FileAdapter(private val listener: OnFileClickListener) : ListAdapter<FileUI, FileViewHolder>(object : DiffUtil.ItemCallback<FileUI>() {
    override fun areItemsTheSame(oldItem: FileUI, newItem: FileUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FileUI, newItem: FileUI): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemFileLayoutBinding.inflate(inflater, parent, false)
        return FileViewHolder(binding) {
            listener.onFileClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}