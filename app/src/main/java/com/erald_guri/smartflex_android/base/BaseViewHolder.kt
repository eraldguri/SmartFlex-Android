package com.erald_guri.smartflex_android.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.interfaces.BaseRecyclerListener

abstract class BaseViewHolder<T, L: BaseRecyclerListener>(itemView: View, listener: L) : RecyclerView.ViewHolder(itemView) {

    private var listener: L? = null

    init {
        this.listener = listener
    }

    abstract fun onBind(item: T)

    fun onBind(item: T, payloads: List<Any>) {
        onBind(item)
    }

    protected fun getListener(): L? {
        return listener
    }

}