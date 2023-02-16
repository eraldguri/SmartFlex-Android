package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.CardModel
import com.erald_guri.smartflex_android.databinding.LayoutCardItemBinding
import com.erald_guri.smartflex_android.interfaces.OnRecyclerItemClickListener

class CardViewHolder(private val binding: LayoutCardItemBinding, listener: OnRecyclerItemClickListener<CardModel>)
    : BaseViewHolder<CardModel, OnRecyclerItemClickListener<CardModel>>(binding.root, listener) {

    override fun onBind(item: CardModel) {
        binding.apply {
            tvTitle.text = item.title
            tvDescription.text = item.description
            root.setCardBackgroundColor(item.colorRes)
        }
        if (getListener() != null) {
            itemView.setOnClickListener {  getListener()?.onItemClick(item) }
        }
    }

}