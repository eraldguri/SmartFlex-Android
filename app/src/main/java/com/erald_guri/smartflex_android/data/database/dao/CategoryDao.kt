package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.*
import com.erald_guri.smartflex_android.data.model.CategoryModel

@Dao
interface CategoryDao {

    @Query("SELECT * FROM CategoryModel")
    suspend fun selectedAll(): List<CategoryModel>

    @Insert
    suspend fun insertCategory(category: CategoryModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategory(category: CategoryModel)

    @Delete
    suspend fun deleteCategory(category: CategoryModel)

}