package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventModel(
    var event: String,
    var time: String,
    var date: String,
    var month: String,
    var year: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
