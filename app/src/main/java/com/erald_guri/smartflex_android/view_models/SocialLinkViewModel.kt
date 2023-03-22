package com.erald_guri.smartflex_android.view_models

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erald_guri.smartflex_android.data.model.ResponseModel
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel
import com.erald_guri.smartflex_android.data.repository.SocialLinkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialLinkViewModel @Inject constructor(private val socialLinkRepository: SocialLinkRepository) : ViewModel() {

    private var _success = MutableLiveData<ResponseModel>()
    val success: LiveData<ResponseModel> = _success

    private var _links = MutableLiveData<List<SocialLinkAccountModel>>()
    val links: LiveData<List<SocialLinkAccountModel>> = _links

    fun fetchAll() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _links.postValue(socialLinkRepository.selectAll())
                _success.postValue(ResponseModel(false, "success"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
            }
        }
    }

    fun insertLink(link: SocialLinkAccountModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                socialLinkRepository.insertLink(link)
                _success.postValue(ResponseModel(false, "${link.title} added successfully"))
            } catch (e: Exception) {
                if (e is SQLiteConstraintException) {
                    _success.postValue(ResponseModel(true, "${link.title} already exists"))
                } else {
                    _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
                }
            }
        }
    }

}