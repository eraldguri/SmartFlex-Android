package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding
import com.erald_guri.smartflex_android.holders.ButtonViewHolder

class ButtonAdapter(
    private val items: ArrayList<ButtonModel>
) : RecyclerView.Adapter<ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutButtonListBinding.inflate(inflater, parent, false)
        return ButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}