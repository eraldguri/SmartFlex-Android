package com.erald_guri.smartflex_android.data.repository

import com.erald_guri.smartflex_android.data.database.dao.EventDao
import com.erald_guri.smartflex_android.data.model.EventModel
import javax.inject.Inject

class EventRepository @Inject constructor(private val eventDao: EventDao) {

    suspend fun selectAll(): List<EventModel> = eventDao.selectAll()

    suspend fun insertEvent(event: EventModel) = eventDao.insertEvent(event)

}