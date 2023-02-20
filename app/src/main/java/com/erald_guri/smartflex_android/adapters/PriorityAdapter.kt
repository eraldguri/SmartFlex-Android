package com.erald_guri.smartflex_android.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding
import com.erald_guri.smartflex_android.holders.PriorityViewHolder
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener

class PriorityAdapter(
    private val items: ArrayList<String>
) : RecyclerView.Adapter<PriorityViewHolder>() {

    lateinit var onClickListener: OnItemClickListener<String>

    private var rowIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriorityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutButtonListBinding.inflate(inflater, parent, false)
        return PriorityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriorityViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]

        holder.onBind(item)

        holder.itemView.setOnClickListener {
            rowIndex = position
            notifyDataSetChanged()
            onClickListener.onItemClick(position, item)
        }

        if (rowIndex == position) {
            holder.setSelectedState()
        } else {
            holder.setUnSelectedState()
        }
    }

    override fun getItemCount(): Int = items.size

    fun setOnItemClickListener(listener: OnItemClickListener<String>) {
        onClickListener = listener
    }
}