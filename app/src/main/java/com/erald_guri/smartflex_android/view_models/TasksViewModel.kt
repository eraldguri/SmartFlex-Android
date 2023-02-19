package com.erald_guri.smartflex_android.view_models

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.data.model.ButtonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(val app: Application) : AndroidViewModel(app) {

    private var _categories = MutableLiveData<ArrayList<ButtonModel>>()
    val categories: LiveData<ArrayList<ButtonModel>> = _categories

    fun categories(isDialogState: Boolean) {
        val drawables = arrayOf(
            ContextCompat.getDrawable(app.applicationContext, R.drawable.ic_baseline_school_24),
            ContextCompat.getDrawable(app.applicationContext, R.drawable.ic_baseline_sports_football_24),
            ContextCompat.getDrawable(app.applicationContext, R.drawable.ic_baseline_edit_24),
            ContextCompat.getDrawable(app.applicationContext, R.drawable.ic_baseline_dry_24),
            ContextCompat.getDrawable(app.applicationContext, R.drawable.ic_baseline_add_24)
        )

        val buttons = ArrayList<ButtonModel>()
        val buttonEducation = ButtonModel(1, "Education", drawables[0])
        val buttonSport = ButtonModel(2, "Sport", drawables[1])
        val buttonMeeting = ButtonModel(3, "Meeting", drawables[2])
        val buttonFriends = ButtonModel(4, "Friends", drawables[3])
        val buttonAdd = ButtonModel(5, "Add New", drawables[4])

        buttons.add(buttonEducation)
        buttons.add(buttonSport)
        buttons.add(buttonMeeting)
        buttons.add(buttonFriends)

        if (!isDialogState) {
            buttons.add(buttonAdd)
        }

        _categories.postValue(buttons)
    }

}