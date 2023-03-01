package com.erald_guri.smartflex_android.view_models

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.*
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.data.model.FontModel
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.data.model.ResponseModel
import com.erald_guri.smartflex_android.data.repository.NoteRepository
import com.erald_guri.smartflex_android.utils.JsonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
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

    private var _actionIcons = MutableLiveData<ArrayList<Int>>()
    val actionIcons: LiveData<ArrayList<Int>> = _actionIcons

    private var _fontListSelector = MutableLiveData<ArrayList<FontModel>>()
    val fontListSelector: LiveData<ArrayList<FontModel>> = _fontListSelector

    private var _colors = MutableLiveData<ArrayList<String>>()
    val colors: LiveData<ArrayList<String>> = _colors

    fun setupNoteActions() {
        val icons = ArrayList<Int>()
        icons.add(R.drawable.ic_baseline_format_color_text_24)
        icons.add(R.drawable.ic_baseline_check_box_24)
        icons.add(R.drawable.ic_baseline_mic_none_24)
        icons.add(R.drawable.ic_baseline_create_24)
        icons.add(R.drawable.ic_baseline_image_24)
        icons.add(R.drawable.ic_baseline_emoji_emotions_24)
        icons.add(R.drawable.ic_baseline_color_lens_24)
        icons.add(R.drawable.ic_baseline_format_list_bulleted_24)
        icons.add(R.drawable.ic_baseline_format_list_numbered_24)

        _actionIcons.postValue(icons)
    }

    fun fetchColors() {
        val colorArray = ArrayList<String>()
        val json = JsonUtils.readJsonFromAsset(app.applicationContext, "color.json")
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject.getJSONArray("colors")
        for (index in 0 until jsonArray.length()) {
            colorArray.add(jsonArray.getString(index))
        }

        _colors.postValue(colorArray)
    }

    fun setupFontListSelector() {
        val fonts = ArrayList<FontModel>()
        fonts.add(FontModel("Amaranth", ResourcesCompat.getFont(app.applicationContext, R.font.amaranth)))
        fonts.add(FontModel("Averia Gruesa Libre", ResourcesCompat.getFont(app.applicationContext, R.font.averia_gruesa_libre)))
        fonts.add(FontModel("Baloo Tamma", ResourcesCompat.getFont(app.applicationContext, R.font.baloo_tamma)))
        fonts.add(FontModel("Bungee", ResourcesCompat.getFont(app.applicationContext, R.font.bungee)))
        fonts.add(FontModel("Lemon", ResourcesCompat.getFont(app.applicationContext, R.font.lemon)))

        _fontListSelector.postValue(fonts)
    }

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