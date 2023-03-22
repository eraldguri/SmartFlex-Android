package com.erald_guri.smartflex_android.data.repository

import com.erald_guri.smartflex_android.data.database.dao.SocialLinkDao
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel
import javax.inject.Inject

class SocialLinkRepository @Inject constructor(private val socialLinkDao: SocialLinkDao) {

    suspend fun selectAll(): List<SocialLinkAccountModel> = socialLinkDao.selectAll()

    suspend fun insertLink(link: SocialLinkAccountModel) = socialLinkDao.insertLink(link)

}