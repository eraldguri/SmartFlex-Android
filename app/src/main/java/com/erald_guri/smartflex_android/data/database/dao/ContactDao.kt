package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erald_guri.smartflex_android.data.model.ContactModel

@Dao
interface ContactDao {

    @Query("SELECT * FROM ContactModel")
    suspend fun selectAll(): List<ContactModel>

    @Insert
    suspend fun insertContact(contact: ContactModel)
}