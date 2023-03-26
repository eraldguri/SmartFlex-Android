package com.erald_guri.smartflex_android.interfaces

interface OnContactListener {

    fun onDetailView(contactId: Int?)

    fun onEditContact(contactId: Int?)

    fun onDeleteContact()

    fun onFavorite()

    fun onCall()

    fun onMessage()
}