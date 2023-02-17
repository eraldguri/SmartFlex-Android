package com.erald_guri.smartflex_android.holders

import android.graphics.Color
import com.erald_guri.smartflex_android.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.CardModel
import com.erald_guri.smartflex_android.databinding.LayoutCardItemChineseBinding

class CardChineseViewHolder(
    private val binding: LayoutCardItemChineseBinding,
): BaseViewHolder<CardModel>(binding.root) {

    override fun onBind(item: CardModel) {
        binding.apply {
            tvChinese.text = item.title
            tvPinyin.text = item.description
            tvEnglish.text = item.secondDescription
            root.setCardBackgroundColor(Color.parseColor(item.color))
        }
    }


}