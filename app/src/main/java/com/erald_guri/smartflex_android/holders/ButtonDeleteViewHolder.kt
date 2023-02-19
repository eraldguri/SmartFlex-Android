package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.ButtonDeleteLayoutBinding

class ButtonDeleteViewHolder(
    private val binding: ButtonDeleteLayoutBinding
) : BaseViewHolder<ButtonModel>(binding.root) {

    override fun onBind(item: ButtonModel) {
        binding.root.apply {
            binding.button.text = item.text
        }
    }

    fun setSelectedState() {
        binding.button.setBackgroundResource(R.drawable.button_round_selected)
    }

    fun setUnSelectedState() {
        binding.button.setBackgroundResource(R.drawable.button_round_corners_unselected)
    }
}