package com.erald_guri.smartflex_android.data.repository

import com.erald_guri.smartflex_android.data.database.dao.AccountDao
import com.erald_guri.smartflex_android.data.model.AccountModel
import javax.inject.Inject

class AccountRepository @Inject constructor(private val accountDao: AccountDao) {

    suspend fun insertAccount(account: AccountModel) = accountDao.insertAccount(account)

}