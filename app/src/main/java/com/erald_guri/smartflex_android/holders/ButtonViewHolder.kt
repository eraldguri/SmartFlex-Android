package com.erald_guri.smartflex_android.holders

import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener

class ButtonViewHolder(
    private val binding: LayoutButtonListBinding,
    private val listener: OnItemClickListener
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ButtonModel) {
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