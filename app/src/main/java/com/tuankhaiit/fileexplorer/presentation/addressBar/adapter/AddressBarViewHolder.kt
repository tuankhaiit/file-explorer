package com.tuankhaiit.fileexplorer.presentation.addressBar.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tuankhaiit.fileexplorer.databinding.ItemAddressBarLayoutBinding
import com.tuankhaiit.fileexplorer.presentation.addressBar.AddressBarUI

class AddressBarViewHolder(
    private val binding: ItemAddressBarLayoutBinding,
    onItemClick: (Int) -> Unit
) : ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            onItemClick.invoke(bindingAdapterPosition)
        }
    }

    fun bind(item: AddressBarUI) {
        binding.txtName.text = item.name
    }
}