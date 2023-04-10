package com.erald_guri.smartflex_android.ui.calendar

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.EventModel
import com.erald_guri.smartflex_android.databinding.LayoutEventSingleItemBinding

class EventViewHolder(private val binding: LayoutEventSingleItemBinding) : BaseViewHolder<EventModel>(binding.root) {

    override fun onBind(item: EventModel) {
        binding.eventName.text = item.event
    }

}