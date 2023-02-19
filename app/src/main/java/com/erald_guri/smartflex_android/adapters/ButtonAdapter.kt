package com.erald_guri.smartflex_android.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.ButtonDeleteLayoutBinding
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding
import com.erald_guri.smartflex_android.holders.ButtonDeleteViewHolder
import com.erald_guri.smartflex_android.holders.ButtonViewHolder
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.interfaces.OnRecyclerItemClickListener
import kotlin.properties.Delegates

class ButtonAdapter(
    private val items: ArrayList<ButtonModel>,
    var isTypeDialog: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onClickListener: OnItemClickListener<ButtonModel>

    private var rowIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TYPE_SIMPLE -> {
                val binding = LayoutButtonListBinding.inflate(inflater, parent, false)
                return ButtonViewHolder(binding)
            }
            TYPE_DIALOG -> {
                val binding = ButtonDeleteLayoutBinding.inflate(inflater, parent, false)
                return ButtonDeleteViewHolder(binding)
            }
        }

        return null!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]

        if (holder is ButtonViewHolder) {
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
        } else if (holder is ButtonDeleteViewHolder) {
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

    }

    override fun getItemCount(): Int = items.size

    fun setOnItemClickListener(listener: OnItemClickListener<ButtonModel>) {
        onClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (!isTypeDialog) {
            TYPE_SIMPLE
        } else {
            TYPE_DIALOG
        }
    }

    companion object {
        const val TYPE_SIMPLE = 0
        const val TYPE_DIALOG = 1
    }
}