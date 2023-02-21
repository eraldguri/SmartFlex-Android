package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskModel(
    @PrimaryKey
    val title: String,
    val priority: String,
    val starts: String,
    val ends: String,
    val location: String,
    val description: String
)
