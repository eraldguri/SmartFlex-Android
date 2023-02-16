package com.erald_guri.smartflex_android.interfaces

interface OnRecyclerItemClickListener<T> : BaseRecyclerListener {

    fun onItemClick(item: T)

}