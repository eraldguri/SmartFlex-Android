package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryModel(
    var title: String,
    var description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}