package com.erald_guri.smartflex_android.holders

import android.graphics.Color
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.SettingsModel
import com.erald_guri.smartflex_android.databinding.LayoutSettingsItemBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener

class SettingsViewHolder(
    private val binding: LayoutSettingsItemBinding,
    private val onItemClickListener: OnItemClickListener<SettingsModel>
) : BaseViewHolder<SettingsModel>(binding.root) {

    override fun onBind(item: SettingsModel) {
        binding.apply {
            tvTitle.text = item.title
            imageView.setImageResource(item.icon)

            root.setOnClickListener {
                onItemClickListener.onItemClick(adapterPosition, item)
            }

            binding.materialCardView.setCardBackgroundColor(Color.parseColor(item.color))
        }
    }

}