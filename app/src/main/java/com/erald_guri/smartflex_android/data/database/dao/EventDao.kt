package com.erald_guri.smartflex_android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erald_guri.smartflex_android.data.model.EventModel

@Dao
interface EventDao {

    @Query("SELECT * FROM EventModel")
    suspend fun selectAll(): List<EventModel>

    @Insert
    suspend fun insertEvent(event: EventModel)

}