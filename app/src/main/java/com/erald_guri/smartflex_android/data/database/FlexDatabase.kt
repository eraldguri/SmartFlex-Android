package com.erald_guri.smartflex_android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erald_guri.smartflex_android.data.database.dao.*
import com.erald_guri.smartflex_android.data.model.*

@Database(
    entities = [
        TaskModel::class,
        CategoryModel::class,
        NoteModel::class,
        ContactModel::class,
        AccountModel::class
    ],
    version = 17
)
abstract class FlexDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun categoryDao(): CategoryDao

    abstract fun noteDao(): NoteDao

    abstract fun contactDao(): ContactDao

    abstract fun accountDao(): AccountDao

}