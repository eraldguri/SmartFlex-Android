package com.erald_guri.smartflex_android.holders

import android.graphics.Color
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.QuotesModel
import com.erald_guri.smartflex_android.databinding.LayoutCardItemBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener

class QuoteViewHolder(
    val binding: LayoutCardItemBinding,
    private val onItemClickListener: OnItemClickListener<QuotesModel>
) : BaseViewHolder<QuotesModel>(binding.root) {

    override fun onBind(item: QuotesModel) {
        binding.apply {
            tvTitle.text = item.title
            root.setCardBackgroundColor(Color.parseColor(item.color))
            root.setOnClickListener {
                onItemClickListener.onItemClick(adapterPosition, item)
            }
        }
    }

}