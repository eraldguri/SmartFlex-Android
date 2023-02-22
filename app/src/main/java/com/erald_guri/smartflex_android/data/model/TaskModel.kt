package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskModel(
    var title: String,
    var priority: String,
    var starts: String,
    var ends: String,
    var location: String,
    var description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
