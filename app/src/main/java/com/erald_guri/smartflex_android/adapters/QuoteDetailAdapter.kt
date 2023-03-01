package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.QuoteContent
import com.erald_guri.smartflex_android.databinding.LayoutQuoteDetailBinding
import com.erald_guri.smartflex_android.holders.QuoteDetailViewHolder

class QuoteDetailAdapter(private val items: ArrayList<QuoteContent>) : RecyclerView.Adapter<QuoteDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutQuoteDetailBinding.inflate(inflater, parent, false)
        return QuoteDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteDetailViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = items.size
}