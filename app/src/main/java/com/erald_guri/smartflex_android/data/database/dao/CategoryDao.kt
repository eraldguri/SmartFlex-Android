package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.erald_guri.smartflex_android.data.model.CategoryModel

@Dao
interface CategoryDao {

    @Query("SELECT * FROM CategoryModel")
    suspend fun selectedAll(): List<CategoryModel>

    @Insert
    suspend fun insertCategory(category: CategoryModel)

    @Delete
    suspend fun deleteCategory(category: CategoryModel)

}