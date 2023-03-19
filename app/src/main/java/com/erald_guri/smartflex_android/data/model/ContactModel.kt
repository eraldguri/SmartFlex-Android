package com.erald_guri.smartflex_android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ContactModel(
    var firstName: String,
    var lastName: String,
    var email: String,
    var title: String,
    var accountName: String,
    var vendorName: String,
    var leadSource: String,
    var dateOfBirth: String,
    var phone: String,
    var otherPhone: String,
    var mobile: String,
    var secondaryEmail: String,
    var street: String,
    var otherStreet: String,
    var city: String,
    var otherCity: String,
    var state: String,
    var otherState: String,
    var country: String,
    var otherCountry: String,
    var zipCode: String,
    var otherZipCode: String,
    var description: String,
    var photoPath: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
