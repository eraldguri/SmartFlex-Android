package com.erald_guri.smartflex_android.holders

import android.graphics.Color
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.databinding.LayoutItemRoundBinding

class ColorViewHolder(val binding: LayoutItemRoundBinding) : BaseViewHolder<String>(binding.root) {

    override fun onBind(item: String) {
        binding.root.setCardBackgroundColor(Color.parseColor(item))
    }


}