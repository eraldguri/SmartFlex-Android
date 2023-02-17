package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.CardModel
import com.erald_guri.smartflex_android.databinding.LayoutCardItemBinding
import com.erald_guri.smartflex_android.databinding.LayoutCardItemChineseBinding
import com.erald_guri.smartflex_android.holders.CardChineseViewHolder
import com.erald_guri.smartflex_android.holders.CardViewHolder

class CardAdapter(
    private val items: ArrayList<CardModel>,
    private val type: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TYPE_SIMPLE_CARD -> {
                val binding = LayoutCardItemBinding.inflate(inflater, parent, false)
                return CardViewHolder(binding)
            }
            TYPE_CHINESE_CARD -> {
                val binding = LayoutCardItemChineseBinding.inflate(inflater, parent, false)
                return CardChineseViewHolder(binding)
            }
        }
        return null!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder.itemViewType == TYPE_SIMPLE_CARD && holder is CardViewHolder) {
            holder.onBind(item)
        } else if (holder.itemViewType == TYPE_CHINESE_CARD && holder is CardChineseViewHolder) {
            holder.onBind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (type == TYPE_SIMPLE_CARD) {
            return TYPE_SIMPLE_CARD
        } else if (type == TYPE_CHINESE_CARD) {
            return TYPE_CHINESE_CARD
        }
        return TYPE_SIMPLE_CARD
    }

    override fun getItemCount(): Int = items.size

    companion object {
        const val TYPE_SIMPLE_CARD = 0
        const val TYPE_CHINESE_CARD = 1
    }

}