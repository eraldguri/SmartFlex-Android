package com.erald_guri.smartflex_android.interfaces

import com.erald_guri.smartflex_android.data.model.TaskModel

interface OnTaskListener {

    fun onItemClick(task: TaskModel)

    fun onEdit(task: TaskModel)

    fun onDelete(position: Int, task: TaskModel)

}