package com.erald_guri.smartflex_android.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding
import com.erald_guri.smartflex_android.holders.ButtonViewHolder
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.interfaces.OnRecyclerItemClickListener
import kotlin.properties.Delegates

class ButtonAdapter(
    private val items: ArrayList<ButtonModel>
) : RecyclerView.Adapter<ButtonViewHolder>() {

    lateinit var onClickListener: OnItemClickListener

    private var rowIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutButtonListBinding.inflate(inflater, parent, false)
        return ButtonViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            rowIndex = position
            if (rowIndex == holder.adapterPosition) {
                holder.setSelectedState(item)
            } else {
                holder.setUnSelectedState(item)
            }
            notifyItemChanged(rowIndex)
        }

    }

    override fun getItemCount(): Int = items.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onClickListener = listener
    }
}