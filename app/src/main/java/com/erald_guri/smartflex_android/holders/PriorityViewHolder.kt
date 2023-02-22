package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding

class PriorityViewHolder(
    private val binding: LayoutButtonListBinding
): BaseViewHolder<String>(binding.root) {

    override fun onBind(item: String) {
        binding.root.apply {
            text = item
        }
    }

    fun setSelectedState() {
        binding.root.setBackgroundResource(R.drawable.button_round_selected)
    }

    fun setUnSelectedState() {
        binding.root.setBackgroundResource(R.drawable.button_round_corners_unselected)
    }

}