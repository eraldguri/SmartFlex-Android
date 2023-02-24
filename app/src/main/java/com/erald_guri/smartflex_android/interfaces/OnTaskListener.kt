package com.erald_guri.smartflex_android.interfaces


interface OnTaskListener<T> {

    fun onItemClick(task: T)

    fun onEdit(position: Int, task: T)

    fun onDelete(position: Int, task: T)

}