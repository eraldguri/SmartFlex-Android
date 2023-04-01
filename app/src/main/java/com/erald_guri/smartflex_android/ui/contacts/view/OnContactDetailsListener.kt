package com.erald_guri.smartflex_android.ui.contacts.view

import com.erald_guri.smartflex_android.data.model.ContactModel

interface OnContactDetailsListener {
    fun getContact(contact: ContactModel)
}