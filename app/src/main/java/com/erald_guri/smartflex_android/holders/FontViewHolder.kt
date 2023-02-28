package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.FontModel
import com.erald_guri.smartflex_android.databinding.LayoutFontChoserBinding

class FontViewHolder(
    val binding: LayoutFontChoserBinding
) : BaseViewHolder<FontModel>(binding.root) {

    override fun onBind(item: FontModel) {
        binding.root.typeface = item.typeface
        binding.root.text = item.name
    }
}