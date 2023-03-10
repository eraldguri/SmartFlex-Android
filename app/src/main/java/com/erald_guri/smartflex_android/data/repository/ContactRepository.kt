package com.erald_guri.smartflex_android.data.repository

import com.erald_guri.smartflex_android.data.database.dao.ContactDao
import com.erald_guri.smartflex_android.data.model.ContactModel
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactDao: ContactDao) {

    suspend fun insertContact(contact: ContactModel) = contactDao.insertContact(contact)

}