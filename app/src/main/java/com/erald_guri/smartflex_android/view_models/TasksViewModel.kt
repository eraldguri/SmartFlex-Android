package com.erald_guri.smartflex_android.view_models

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.data.model.TaskModel
import com.erald_guri.smartflex_android.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(val app: Application, private val taskRepository: TaskRepository)
    : AndroidViewModel(app) {

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

    fun saveTask(task: TaskModel) {
        viewModelScope.launch {
            taskRepository.createTask(task)
        }
    }

}