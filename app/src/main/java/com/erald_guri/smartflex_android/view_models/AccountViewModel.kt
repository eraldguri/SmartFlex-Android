package com.erald_guri.smartflex_android.view_models

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erald_guri.smartflex_android.data.model.AccountModel
import com.erald_guri.smartflex_android.data.model.ResponseModel
import com.erald_guri.smartflex_android.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val accountRepository: AccountRepository) : ViewModel() {

    private var _success = MutableLiveData<ResponseModel>()
    val success: LiveData<ResponseModel> = _success

    fun insertAccount(account: AccountModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                accountRepository.insertAccount(account)
                _success.postValue(ResponseModel(false, "${account.accountName} added successfully"))
            } catch (e: Exception) {
                if (e is SQLiteConstraintException) {
                    _success.postValue(ResponseModel(true, "${account.accountName} already exists"))
                } else {
                    _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
                }
            }
        }
    }

}