package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.QuotesModel
import com.erald_guri.smartflex_android.databinding.LayoutCardItemBinding
import com.erald_guri.smartflex_android.holders.QuoteViewHolder
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener

class QuoteAdapter(
    private val quotes: ArrayList<QuotesModel>,
    private val onItemClickListener: OnItemClickListener<QuotesModel>
) : RecyclerView.Adapter<QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutCardItemBinding.inflate(inflater, parent, false)
        return QuoteViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        holder.onBind(quote)
    }

    override fun getItemCount(): Int = quotes.size
}