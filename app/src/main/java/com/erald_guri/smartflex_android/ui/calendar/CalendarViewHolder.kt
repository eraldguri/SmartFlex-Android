package com.erald_guri.smartflex_android.ui.calendar

import android.content.Context
import androidx.core.content.ContextCompat
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.EventModel
import com.erald_guri.smartflex_android.databinding.LayoutCalendarSingleCellBinding
import java.util.Date

class CalendarViewHolder(
    val context: Context,
    val binding: LayoutCalendarSingleCellBinding
) : BaseViewHolder<Date>(binding.root) {

    override fun onBind(item: Date) {
//        binding.tvDay.text = item.toString()
    }

    fun onChange(displayMonth: Int, currentMonth: Int, displayYear: Int, currentYear: Int) {
        if (displayMonth == currentMonth && displayYear == currentYear) {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        } else {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

}