package com.erald_guri.smartflex_android.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(item: T)

}