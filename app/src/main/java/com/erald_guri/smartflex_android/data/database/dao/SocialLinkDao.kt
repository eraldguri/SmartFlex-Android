package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel

@Dao
interface SocialLinkDao {

    @Query("SELECT * FROM SocialLinkAccountModel")
    suspend fun selectAll(): List<SocialLinkAccountModel>

    @Insert
    suspend fun insertLink(link: SocialLinkAccountModel)

}