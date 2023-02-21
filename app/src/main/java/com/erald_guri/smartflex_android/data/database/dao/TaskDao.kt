package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.erald_guri.smartflex_android.data.model.TaskModel

@Dao
interface TaskDao {

    @Insert
    suspend fun saveTask(task: TaskModel)

}