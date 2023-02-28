package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.databinding.LayoutNoteToolsBinding
import com.erald_guri.smartflex_android.holders.NoteToolsViewHolder
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener

class NoteToolsAdapter(
    private val items: ArrayList<Int>,
    private val onItemClickListener: OnItemClickListener<Int>
) : RecyclerView.Adapter<NoteToolsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteToolsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutNoteToolsBinding.inflate(inflater, parent, false)
        return NoteToolsViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: NoteToolsViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int = items.size
}