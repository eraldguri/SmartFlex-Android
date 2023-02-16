package com.erald_guri.smartflex_android.base

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallback<T>(private val oldItems: ArrayList<T>, private val newItems: ArrayList<T>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    @JvmName("getOldItems1")
    fun getOldItems(): ArrayList<T> = oldItems

    @JvmName("getNewItems1")
    fun getNewItems(): ArrayList<T> = newItems

}