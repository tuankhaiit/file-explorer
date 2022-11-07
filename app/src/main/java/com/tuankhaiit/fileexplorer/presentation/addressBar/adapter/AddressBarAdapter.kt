package com.tuankhaiit.fileexplorer.presentation.addressBar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tuankhaiit.fileexplorer.databinding.ItemAddressBarLayoutBinding
import com.tuankhaiit.fileexplorer.presentation.addressBar.AddressBarUI

class AddressBarAdapter(private val listener: OnAddressBarClickListener) :
    ListAdapter<AddressBarUI, AddressBarViewHolder>(object : DiffUtil.ItemCallback<AddressBarUI>() {
        override fun areContentsTheSame(oldItem: AddressBarUI, newItem: AddressBarUI): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areItemsTheSame(oldItem: AddressBarUI, newItem: AddressBarUI): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressBarViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemAddressBarLayoutBinding.inflate(inflater, parent, false)
        return AddressBarViewHolder(binding) {
            listener.onAddressBarClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: AddressBarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}