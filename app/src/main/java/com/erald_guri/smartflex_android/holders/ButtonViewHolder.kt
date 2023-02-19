package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding

class ButtonViewHolder(
    private val binding: LayoutButtonListBinding
): BaseViewHolder<ButtonModel>(binding.root) {

    override fun onBind(item: ButtonModel) {
        binding.root.apply {
            text = item.text
            setCompoundDrawablesWithIntrinsicBounds(item.icon, null, null, null)
            compoundDrawablePadding = 16
        }
    }

    fun setSelectedState() {
        binding.root.setBackgroundResource(R.drawable.button_round_selected)
    }

    fun setUnSelectedState() {
        binding.root.setBackgroundResource(R.drawable.button_round_corners_unselected)
    }

}