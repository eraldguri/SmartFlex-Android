package com.erald_guri.smartflex_android.holders

import android.graphics.Color
import com.erald_guri.smartflex_android.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.CardModel
import com.erald_guri.smartflex_android.databinding.LayoutCardItemBinding

class CardViewHolder(
    private val binding: LayoutCardItemBinding): BaseViewHolder<CardModel>(binding.root) {

    override fun onBind(item: CardModel) {
        binding.apply {
            tvTitle.text = item.title
            tvDescription.text = item.description
            root.setCardBackgroundColor(Color.parseColor(item.color))
        }

    }

}