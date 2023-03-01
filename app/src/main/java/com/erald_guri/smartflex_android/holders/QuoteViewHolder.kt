package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.QuotesModel
import com.erald_guri.smartflex_android.databinding.LayoutCardItemBinding

class QuoteViewHolder(val binding: LayoutCardItemBinding) : BaseViewHolder<QuotesModel>(binding.root) {

    override fun onBind(item: QuotesModel) {
        binding.apply {
            tvTitle.text = item.title
        }
    }

}