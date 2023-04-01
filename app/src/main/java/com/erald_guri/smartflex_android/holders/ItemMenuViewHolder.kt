package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.ItemMenuModel
import com.erald_guri.smartflex_android.databinding.LayoutMenuItemBinding

class ItemMenuViewHolder(val binding: LayoutMenuItemBinding) : BaseViewHolder<ItemMenuModel>(binding.root) {

    override fun onBind(item: ItemMenuModel) {
        binding.apply {
            imgIcon.setImageResource(item.icon)
            tvTitle.text = "${item.title} (${item.numItems})"
        }
    }
}