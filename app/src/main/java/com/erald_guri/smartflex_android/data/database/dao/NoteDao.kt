package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.*
import com.erald_guri.smartflex_android.data.model.NoteModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteModel")
    suspend fun selectAll(): List<NoteModel>

    @Insert
    fun saveNote(note: NoteModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: NoteModel)

    @Delete
    suspend fun deleteNote(note: NoteModel)

}