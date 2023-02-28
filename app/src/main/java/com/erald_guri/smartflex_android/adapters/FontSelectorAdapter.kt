package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.FontModel
import com.erald_guri.smartflex_android.databinding.LayoutFontChoserBinding
import com.erald_guri.smartflex_android.holders.FontViewHolder

class FontSelectorAdapter(
    private val typefaces: ArrayList<FontModel>
) : RecyclerView.Adapter<FontViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutFontChoserBinding.inflate(inflater, parent, false)
        return FontViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FontViewHolder, position: Int) {
        val typeface = typefaces[position]
        holder.onBind(typeface)
    }

    override fun getItemCount(): Int = typefaces.size
}