package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.databinding.LayoutItemRoundBinding
import com.erald_guri.smartflex_android.holders.ColorViewHolder

class ColorSelectorAdapter(private val items: ArrayList<String>) : RecyclerView.Adapter<ColorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemRoundBinding.inflate(inflater, parent, false)
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = items[position]
        holder.onBind(color)

    }

    override fun getItemCount(): Int = items.size

}