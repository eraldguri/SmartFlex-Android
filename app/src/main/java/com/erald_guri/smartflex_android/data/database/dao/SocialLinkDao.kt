package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.*
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel

@Dao
interface SocialLinkDao {

    @Query("SELECT * FROM SocialLinkAccountModel")
    suspend fun selectAll(): List<SocialLinkAccountModel>

    @Insert
    suspend fun insertLink(link: SocialLinkAccountModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLink(link: SocialLinkAccountModel)

    @Delete
    suspend fun deleteLink(link: SocialLinkAccountModel)

}