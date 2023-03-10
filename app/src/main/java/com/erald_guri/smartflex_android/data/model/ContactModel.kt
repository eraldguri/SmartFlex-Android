package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ContactModel(
    var firstName: String,
    var lastName: String,
    var department: String,
    var email: String,
    var title: String,
    var phone: Int,
    var otherPhone: Int,
    var mobile: Int,
    var homePhone: Int,
    var dateOfBirth: String,
    var street: String,
    var otherStreet: String,
    var city: String,
    var otherCity: String,
    var state: String,
    var otherState: String,
    var country: String,
    var otherCountry: String,
    var zipCode: Int,
    var otherZipCode: Int,
    var description: String,
    var photoPath: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    constructor()
            : this("", "", "", "", "", -1, -1,
        -1, -1, "", "", "", "", "", "", "",
        "", "", -1, -1, "", "")
}
