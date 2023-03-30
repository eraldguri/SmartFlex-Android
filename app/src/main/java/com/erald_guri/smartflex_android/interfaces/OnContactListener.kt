package com.erald_guri.smartflex_android.interfaces

import com.erald_guri.smartflex_android.data.model.ContactModel

interface OnContactListener {

    fun onDetailView(contactId: Int?)

    fun onEditContact(contactId: Int?)

    fun onDeleteContact()

    fun onFavorite(contact: ContactModel, isFavorite: Boolean)

    fun onCall(phone: String)

    fun onMessage()
}