package com.erald_guri.smartflex_android.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.EventModel
import com.erald_guri.smartflex_android.databinding.FragmentCalendarBinding
import com.erald_guri.smartflex_android.databinding.LayoutNewEventBinding
import com.erald_guri.smartflex_android.view_models.EventViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CalendarFragment : BaseFragment<FragmentCalendarBinding>(
    FragmentCalendarBinding::inflate
) {

    private var calendar = Calendar.getInstance(Locale.ENGLISH)
    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val monthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
    private val yearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)

    private var dates = ArrayList<Date>()

    private var dialogBinding: LayoutNewEventBinding? = null

    private val viewModel by viewModels<EventViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendar()

        binding.apply {
            prevBtn.setOnClickListener {
                calendar.add(Calendar.MONTH, -1)
                setupCalendar()
            }

            nextBtn.setOnClickListener {
                calendar.add(Calendar.MONTH, 1)
                setupCalendar()
            }

            gridView.setOnItemClickListener { parent, view, position, id ->
                showBottomDialog(position)
            }
        }
    }

    private fun showBottomDialog(position: Int) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        dialogBinding = LayoutNewEventBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogBinding!!.root)

        dialogBinding?.apply {
            edEventTime.setOnClickListener {
                showTimePickerDialog()
            }

            btnAddEvent.setOnClickListener {
                prepareEvent(position)
            }
        }

        bottomSheetDialog.show()
    }

    private fun prepareEvent(position: Int) {
        val savedDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = savedDateFormat.format(dates[position])
        val month = monthFormat.format(dates[position])
        val year = yearFormat.format(dates[position])

        val eventName = dialogBinding?.edEventName?.text.toString()
        val eventTime = dialogBinding?.edEventTime?.text.toString()

        val eventModel = EventModel(eventName, eventTime , date, month, year)

        if (eventName.isNotEmpty() && eventTime.isNotEmpty()) {
            addEvent(eventModel)
        } else {
            Toast.makeText(requireContext(), "Event name and time are required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addEvent(event: EventModel) {
        viewModel.addEvent(event)
        viewModel.success.observe(viewLifecycleOwner) {
            if (!it.error) {
                Log.d("success", it.message)
            } else {
                Log.d("error", it.message)
            }
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog.newInstance(onTimeSet, hours, minutes, false)

        childFragmentManager.let { timePickerDialog.show(it, "Timepickerdialog") };
    }

    private val onTimeSet = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute, second ->
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)
        c.timeZone = TimeZone.getDefault()

        val hourFormat = SimpleDateFormat("K:mm a", Locale.getDefault())

        val eventTime = hourFormat.format(c.time)
        dialogBinding?.edEventTime?.setText(eventTime)
    }

    private fun setupCalendar() {
        val currentDate = dateFormat.format(calendar.time)
        binding.tvCurrentDate.text = currentDate

        dates.clear()

        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK - 1)
        monthCalendar.add(Calendar.DAY_OF_MONTH, firstDayOfMonth)

        while (dates.size < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        viewModel.fetchEvents()
        viewModel.events.observe(viewLifecycleOwner) {
            val calendarAdapter = CalendarAdapter(requireContext(), calendar, dates, it)
            binding.gridView.adapter = calendarAdapter

            Log.d("events", it.toString())
        }

    }

    companion object {
        const val MAX_CALENDAR_DAYS = 35
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {

    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}