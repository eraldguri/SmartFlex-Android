package com.erald_guri.smartflex_android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erald_guri.smartflex_android.data.database.dao.TaskDao
import com.erald_guri.smartflex_android.data.model.TaskModel

@Database(
    entities = [
        TaskModel::class
    ],
    version = 1
)
abstract class FlexDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}