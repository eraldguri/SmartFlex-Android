package com.erald_guri.smartflex_android.view_models

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erald_guri.smartflex_android.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(val app: Application) : AndroidViewModel(app) {

    private var _priorities = MutableLiveData<ArrayList<String>>()
    val priorities: LiveData<ArrayList<String>> = _priorities

    fun priorities() {
        val priorityList = ArrayList<String>()
        priorityList.add("Low")
        priorityList.add("Medium")
        priorityList.add("High")
        priorityList.add("Urgent")
        _priorities.postValue(priorityList)
    }

}