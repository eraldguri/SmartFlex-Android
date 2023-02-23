package com.erald_guri.smartflex_android.data.repository

import com.erald_guri.smartflex_android.data.database.dao.CategoryDao
import com.erald_guri.smartflex_android.data.model.CategoryModel
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    suspend fun selectedAll(): List<CategoryModel> = categoryDao.selectedAll()

    suspend fun insertCategory(category: CategoryModel) = categoryDao.insertCategory(category)

    suspend fun deleteCategory(category: CategoryModel) = categoryDao.deleteCategory(category)

}