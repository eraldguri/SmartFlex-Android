package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.*
import com.erald_guri.smartflex_android.data.model.ContactModel

@Dao
interface ContactDao {

    @Query("SELECT * FROM Contact")
    suspend fun selectAll(): List<ContactModel>

    @Query("SELECT * FROM Contact WHERE id=:id")
    suspend fun getContactById(id: Int): ContactModel

    @Insert
    suspend fun insertContact(contact: ContactModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateContact(contact: ContactModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(contact: ContactModel)
}