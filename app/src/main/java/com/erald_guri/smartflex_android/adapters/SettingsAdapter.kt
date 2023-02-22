package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.SettingsModel
import com.erald_guri.smartflex_android.databinding.LayoutSettingsItemBinding
import com.erald_guri.smartflex_android.holders.SettingsViewHolder
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener

class SettingsAdapter(
    private val settings: List<SettingsModel>,
    private val onItemClickListener: OnItemClickListener<SettingsModel>
) : RecyclerView.Adapter<SettingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutSettingsItemBinding.inflate(inflater, parent, false)
        return SettingsViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val setting = settings[position]
        holder.onBind(setting)
    }

    override fun getItemCount(): Int = settings.size
}