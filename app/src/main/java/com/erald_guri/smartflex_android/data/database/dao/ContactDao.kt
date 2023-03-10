package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.erald_guri.smartflex_android.data.model.ContactModel

@Dao
interface ContactDao {

    @Insert
    suspend fun insertContact(contact: ContactModel)
}