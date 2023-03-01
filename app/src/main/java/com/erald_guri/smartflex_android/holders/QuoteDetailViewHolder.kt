package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.QuoteContent
import com.erald_guri.smartflex_android.data.model.QuotesModel
import com.erald_guri.smartflex_android.databinding.LayoutQuoteDetailBinding

class QuoteDetailViewHolder(private val binding: LayoutQuoteDetailBinding) : BaseViewHolder<QuoteContent>(binding.root) {

    override fun onBind(item: QuoteContent) {
        binding.apply {
            tvTitle.text = "${item.no} ${item.title}"

            val stringBuilder = StringBuilder()
            for (str in item.descriptions) {
                stringBuilder.append(str + "\n")
            }
            tvDescription.text = stringBuilder.toString()
        }
    }

}