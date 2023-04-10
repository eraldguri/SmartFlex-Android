package com.erald_guri.smartflex_android.view_models

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erald_guri.smartflex_android.data.model.EventModel
import com.erald_guri.smartflex_android.data.model.ResponseModel
import com.erald_guri.smartflex_android.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventRepository: EventRepository) : ViewModel() {

    private var _success = MutableLiveData<ResponseModel>()
    val success: LiveData<ResponseModel> = _success

    private var _events = MutableLiveData<List<EventModel>>()
    val events: LiveData<List<EventModel>> = _events

    fun fetchEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _events.postValue(eventRepository.selectAll())
                _success.postValue(ResponseModel(false, "success"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
            }
        }
    }

    fun fetchEventsByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _events.postValue(eventRepository.selectEventsByDate(date))
                _success.postValue(ResponseModel(false, "success"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
            }
        }
    }

    fun addEvent(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                eventRepository.insertEvent(event)
                _success.postValue(ResponseModel(false, "${event.event}  added successfully"))
            } catch (e: Exception) {
                if (e is SQLiteConstraintException) {
                    _success.postValue(ResponseModel(true, "${event.event} already exists"))
                } else {
                    _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
                }
            }
        }
    }

}