package com.erald_guri.smartflex_android.view_models

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.*
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.data.model.ResponseModel
import com.erald_guri.smartflex_android.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {

    private var _success = MutableLiveData<ResponseModel>()
    val success: LiveData<ResponseModel> = _success

    private var _notes = MutableLiveData<List<NoteModel>>()
    val notes: LiveData<List<NoteModel>> = _notes.distinctUntilChanged()

    fun fetchNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _notes.postValue(noteRepository.selectAll())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertNote(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.insertNote(note)
                _success.postValue(ResponseModel(false, "${note.title} added successfully"))
            } catch (e: Exception) {
                if (e is SQLiteConstraintException) {
                    _success.postValue(ResponseModel(true, "${note.title} already exists"))
                } else {
                    _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
                }
            }
        }
    }

    fun updateNote(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.updateNote(note)
                _success.postValue(ResponseModel(false, "${note.title} updated successfully"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
            }
        }
    }

    fun deleteNote(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.deleteNote(note)
                _success.postValue(ResponseModel(false, "${note.title} deleted successfully"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
            }
        }
    }

}