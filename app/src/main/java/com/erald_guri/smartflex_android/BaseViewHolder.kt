package com.erald_guri.smartflex_android

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(item: T)

}