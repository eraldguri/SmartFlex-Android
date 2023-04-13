package com.erald_guri.smartflex_android.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<T> internal constructor(
    private val binding: ViewBinding,
    private val expression: (T, ViewBinding) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: T) {
        expression(item, binding)
    }

    fun onBind(item: T, onItemClickListener: FlexAdapter.OnItemClickListener<T>?) {
        expression(item, binding)

        binding.root.setOnClickListener {
            onItemClickListener?.onClick(adapterPosition, item)
        }

    }

}