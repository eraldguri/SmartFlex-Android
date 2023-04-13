package com.erald_guri.smartflex_android.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class FlexAdapter<T>: RecyclerView.Adapter<BaseViewHolder<T>>(){

    var items: MutableList<T>? = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClickListener: OnItemClickListener<T>? = null
    var expressionViewHolderBinding: ((T,ViewBinding) -> Unit)? = null
    var expressionOnCreateViewHolder:((ViewGroup)->ViewBinding)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return expressionOnCreateViewHolder?.let { it(parent) }?.let { BaseViewHolder(it, expressionViewHolderBinding!!) }!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = items!![position]
        holder.onBind(item, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    fun setOnClickListener(listener: OnItemClickListener<T>?) {
        onItemClickListener = listener
    }

    interface OnItemClickListener<T> {
        fun onClick(position: Int, item: T)
    }
}