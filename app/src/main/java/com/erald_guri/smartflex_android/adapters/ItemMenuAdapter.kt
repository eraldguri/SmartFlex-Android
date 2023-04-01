package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.ItemMenuModel
import com.erald_guri.smartflex_android.databinding.LayoutMenuItemBinding
import com.erald_guri.smartflex_android.holders.ItemMenuViewHolder

class ItemMenuAdapter(private val items: List<ItemMenuModel>) : RecyclerView.Adapter<ItemMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutMenuItemBinding.inflate(inflater, parent, false)
        return ItemMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemMenuViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = items.size
}