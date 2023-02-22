package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.erald_guri.smartflex_android.data.model.TaskModel

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskModel")
    suspend fun selectAll(): List<TaskModel>

    @Insert
    suspend fun saveTask(task: TaskModel)

    @Delete
    suspend fun deleteTask(task: TaskModel)

}