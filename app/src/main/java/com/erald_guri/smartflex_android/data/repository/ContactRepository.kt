package com.erald_guri.smartflex_android.data.repository

import com.erald_guri.smartflex_android.data.database.dao.ContactDao
import com.erald_guri.smartflex_android.data.model.ContactModel
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contactDao: ContactDao) {

    suspend fun selectAll() = contactDao.selectAll()

    suspend fun getContactById(id: Int): ContactModel = contactDao.getContactById(id)

    suspend fun insertContact(contact: ContactModel) = contactDao.insertContact(contact)

    suspend fun updateContact(contact: ContactModel) = contactDao.updateContact(contact)

    suspend fun addToFavorites(contact: ContactModel) = contactDao.addToFavorites(contact)

}