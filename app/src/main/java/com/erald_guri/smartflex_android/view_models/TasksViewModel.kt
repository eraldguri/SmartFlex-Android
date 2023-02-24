package com.erald_guri.smartflex_android.view_models

import android.app.Application
import androidx.lifecycle.*
import com.erald_guri.smartflex_android.data.model.TaskModel
import com.erald_guri.smartflex_android.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(val app: Application, private val taskRepository: TaskRepository)
    : AndroidViewModel(app) {

    private var _priorities = MutableLiveData<ArrayList<String>>()
    val priorities: LiveData<ArrayList<String>> = _priorities

    private var _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> = _tasks

    fun priorities() {
        val priorityList = ArrayList<String>()
        priorityList.add("Low")
        priorityList.add("Medium")
        priorityList.add("High")
        priorityList.add("Urgent")
        _priorities.postValue(priorityList)
    }

    fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _tasks.postValue(taskRepository.getAllTasks())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveTask(task: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taskRepository.createTask(task)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeTask(task: TaskModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                taskRepository.deleteTask(task)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}