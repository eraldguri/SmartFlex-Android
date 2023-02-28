package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteModel(
    var title: String,
    var description: String,
    var dateTime: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var draft: Int? = null
}
