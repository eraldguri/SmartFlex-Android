package com.erald_guri.smartflex_android.adapters

import android.content.Context
import android.view.ViewGroup
import com.erald_guri.smartflex_android.base.BaseRecyclerAdapter
import com.erald_guri.smartflex_android.data.model.CardModel
import com.erald_guri.smartflex_android.databinding.LayoutCardItemBinding
import com.erald_guri.smartflex_android.holders.CardViewHolder
import com.erald_guri.smartflex_android.interfaces.OnRecyclerItemClickListener

class CardAdapter(
    context: Context,
    private val listener: OnRecyclerItemClickListener<CardModel>
) : BaseRecyclerAdapter<CardModel, OnRecyclerItemClickListener<CardModel>, CardViewHolder>(context, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = LayoutCardItemBinding.inflate(layoutInflater!!, parent, false)
        return CardViewHolder(binding, listener)
    }

}