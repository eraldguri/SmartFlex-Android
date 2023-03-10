package com.erald_guri.smartflex_android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erald_guri.smartflex_android.data.database.dao.CategoryDao
import com.erald_guri.smartflex_android.data.database.dao.ContactDao
import com.erald_guri.smartflex_android.data.database.dao.NoteDao
import com.erald_guri.smartflex_android.data.database.dao.TaskDao
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.data.model.TaskModel

@Database(
    entities = [
        TaskModel::class,
        CategoryModel::class,
        NoteModel::class,
        ContactModel::class
    ],
    version = 6
)
abstract class FlexDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun categoryDao(): CategoryDao

    abstract fun noteDao(): NoteDao

    abstract fun contactDao(): ContactDao

}