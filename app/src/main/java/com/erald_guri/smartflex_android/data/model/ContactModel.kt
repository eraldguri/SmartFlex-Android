package com.erald_guri.smartflex_android.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Contact",
    indices = [
        Index("firstName", unique = true),
        Index("lastName", unique = true)
    ]
)
class ContactModel(
    var firstName: String,
    var lastName: String,
    var email: String,
    var title: String,
    var company: String,
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
    var photoPath: String,
    var isFavorite: Int = 0
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    constructor() : this(
        "", "", "", "", "",
        "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "",
        "", "", "", "", ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(title)
        parcel.writeString(company)
        parcel.writeString(accountName)
        parcel.writeString(vendorName)
        parcel.writeString(leadSource)
        parcel.writeString(dateOfBirth)
        parcel.writeString(phone)
        parcel.writeString(otherPhone)
        parcel.writeString(mobile)
        parcel.writeString(secondaryEmail)
        parcel.writeString(street)
        parcel.writeString(otherStreet)
        parcel.writeString(city)
        parcel.writeString(otherCity)
        parcel.writeString(state)
        parcel.writeString(otherState)
        parcel.writeString(country)
        parcel.writeString(otherCountry)
        parcel.writeString(zipCode)
        parcel.writeString(otherZipCode)
        parcel.writeString(description)
        parcel.writeString(photoPath)
        parcel.writeInt(isFavorite)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactModel> {
        override fun createFromParcel(parcel: Parcel): ContactModel {
            return ContactModel(parcel)
        }

        override fun newArray(size: Int): Array<ContactModel?> {
            return arrayOfNulls(size)
        }
    }
}
