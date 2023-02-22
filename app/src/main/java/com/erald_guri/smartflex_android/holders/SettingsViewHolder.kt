package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.SettingsModel
import com.erald_guri.smartflex_android.databinding.LayoutSettingsItemBinding

class SettingsViewHolder(private val binding: LayoutSettingsItemBinding) : BaseViewHolder<SettingsModel>(binding.root) {

    override fun onBind(item: SettingsModel) {
        binding.apply {
            tvTitle.text = item.title
            imageView.setImageResource(item.icon)
        }
    }

}