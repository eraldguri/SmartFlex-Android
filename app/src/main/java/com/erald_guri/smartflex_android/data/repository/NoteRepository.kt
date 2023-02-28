package com.erald_guri.smartflex_android.data.repository

import com.erald_guri.smartflex_android.data.database.dao.NoteDao
import com.erald_guri.smartflex_android.data.model.NoteModel
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    suspend fun selectAll(): List<NoteModel> = noteDao.selectAll()

    suspend fun insertNote(note: NoteModel) = noteDao.saveNote(note)

    suspend fun updateNote(note: NoteModel) = noteDao.updateNote(note)

    suspend fun deleteNote(note: NoteModel) = noteDao.deleteNote(note)

}