package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("title", unique = true)])
data class CategoryModel(
    var title: String,
    var description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}