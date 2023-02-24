package com.erald_guri.smartflex_android.view_models

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.*
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.data.model.ResponseModel
import com.erald_guri.smartflex_android.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel() {

    private var _categories = MutableLiveData<List<CategoryModel>>()
    val categories: LiveData<List<CategoryModel>> = _categories.distinctUntilChanged()

    private var _success = MutableLiveData<ResponseModel>()
    val success: LiveData<ResponseModel> = _success

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
                _success.postValue(ResponseModel(false, "${category.title} added successfully"))
            } catch (e: Exception) {
                if (e is SQLiteConstraintException) {
                    _success.postValue(ResponseModel(true, "${category.title} already exists"))
                } else {
                    _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
                }
            }
        }
    }

    fun updateCategory(category: CategoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryRepository.updateCategory(category)
                _success.postValue(ResponseModel(false, "${category.title} updated successfully"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred. Please try again!"))
            }
        }
    }

    fun deleteCategory(category: CategoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categoryRepository.deleteCategory(category)
                _success.postValue(ResponseModel(false, "${category.title} deleted successfully"))
            } catch (e: Exception) {
                _success.postValue(ResponseModel(true, "An error occurred"))
                e.printStackTrace()
            }
        }
    }

}