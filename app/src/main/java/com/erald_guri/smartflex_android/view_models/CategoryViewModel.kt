package com.erald_guri.smartflex_android.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel() {

    private var _categories = MutableLiveData<List<CategoryModel>>()
    val categories: LiveData<List<CategoryModel>> = _categories

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    fun selectedAll(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _categories.postValue(categoryRepository.selectedAll())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertCategory(category: CategoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryRepository.insertCategory(category)
                _success.postValue(true)
            } catch (e: Exception) {
                _success.postValue(false)
                e.printStackTrace()
            }
        }
    }

    fun deleteCategory(category: CategoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryRepository.deleteCategory(category)
                _success.postValue(true)
            } catch (e: Exception) {
                _success.postValue(false)
                e.printStackTrace()
            }
        }
    }

}