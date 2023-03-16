package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountModel(
    var accountName: String,
    var accountPhone: Int,
    var accountWebsite: String,
    var accountSite: String,
    var fax: String,
    var accountType: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
