package com.tuankhaiit.fileexplorer.presentation.container.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tuankhaiit.fileexplorer.databinding.ItemFileLayoutBinding
import com.tuankhaiit.fileexplorer.presentation.container.FileUI

class FileViewHolder(private val binding: ItemFileLayoutBinding, onItemClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            onItemClick.invoke(bindingAdapterPosition)
        }
    }

    fun bind(fileUI: FileUI) {
        binding.txtName.text = fileUI.name
        binding.txtDate.text = fileUI.date
        binding.txtInfo.text = fileUI.info
        binding.imgIcon.setImageResource(fileUI.icon)
    }
}