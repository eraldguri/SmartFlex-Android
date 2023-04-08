package com.erald_guri.smartflex_android.ui.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.data.model.EventModel
import com.erald_guri.smartflex_android.databinding.LayoutCalendarSingleCellBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(
    private val context: Context,
    private val currentDate: Calendar,
    private val dates: List<Date>,
    private val events: List<EventModel>,
) : BaseAdapter() {

    override fun getCount(): Int = dates.size

    override fun getItem(position: Int): Any = dates[position]

    override fun getItemId(position: Int): Long {
        return dates[position].time
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = LayoutCalendarSingleCellBinding.inflate(inflater, parent, false)


        val monthDate = dates[position]
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = monthDate

        val dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)
        val displayMonth = dateCalendar.get(Calendar.MONTH) + 1
        val displayYear = dateCalendar.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH + 1)
        val currentYear = currentDate.get(Calendar.YEAR)

        if (displayMonth == currentMonth && displayYear == currentYear) {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
        } else {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        binding.tvCalendarDate.text = "$dayNo"

        val eventCalendar = Calendar.getInstance()
        val arrayList = ArrayList<String>()
        events.forEachIndexed { index, eventModel ->
            eventCalendar.time = convertStringToDate(events[index].date)
            if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH)
                && displayMonth == eventCalendar.get(Calendar.MONTH + 1) && displayYear == eventCalendar.get(Calendar.YEAR)) {
                arrayList.add(events[index].event)
            }
        }

        if (events.isNotEmpty()) {
            binding.tvEventId.text = "${arrayList.size} Events"
        } else {
            binding.tvEventId.text = ""
        }

        return binding.root
    }

    private fun convertStringToDate(eventDate: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = format.parse(eventDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date!!
    }

}