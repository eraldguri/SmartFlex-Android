package com.erald_guri.smartflex_android.data.repository

import com.erald_guri.smartflex_android.data.database.dao.TaskDao
import com.erald_guri.smartflex_android.data.model.TaskModel
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun createTask(task: TaskModel) = taskDao.saveTask(task)

}