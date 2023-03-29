package com.erald_guri.smartflex_android.view_models

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.data.model.ResponseModel
import com.erald_guri.smartflex_android.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val contactRepository: ContactRepository): ViewModel() {

    private var _success = MutableLiveData<ResponseModel>()
    val success: LiveData<ResponseModel> = _success

    private var _contacts = MutableLiveData<List<ContactModel>>()
    val contacts: LiveData<List<ContactModel>> = _contacts

    private var _contact = MutableLiveData<ContactModel>()
    val contact: LiveData<ContactModel> = _contact

    fun fetchContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _contacts.postValue(contactRepository.selectAll())
                _success.postValue(ResponseModel(false, "success"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
            }
        }
    }

    fun getContactById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _contact.postValue(contactRepository.getContactById(id))
                _success.postValue(ResponseModel(false, "success"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
            }
        }
    }

    fun insertContact(contact: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                contactRepository.insertContact(contact)
                _success.postValue(ResponseModel(false, "${contact.firstName} ${contact.lastName} added successfully"))
            } catch (e: Exception) {
                if (e is SQLiteConstraintException) {
                    _success.postValue(ResponseModel(true, "${contact.firstName} ${contact.lastName} already exists"))
                } else {
                    _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
                }
            }
        }
    }

    fun updateContact(contact: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                contactRepository.updateContact(contact)
                _success.postValue(ResponseModel(false, "${contact.firstName} ${contact.lastName} added successfully"))
            } catch (e: Exception) {
                if (e is SQLiteConstraintException) {
                    _success.postValue(ResponseModel(true, "${contact.firstName} ${contact.lastName} already exists"))
                } else {
                    _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
                }
            }
        }
    }

}