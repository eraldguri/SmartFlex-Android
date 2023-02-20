package com.erald_guri.smartflex_android.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.adapters.PriorityAdapter
import com.erald_guri.smartflex_android.databinding.FragmentTasksBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.view_models.TasksViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class TasksFragment : BaseFragment<FragmentTasksBinding>(
    FragmentTasksBinding::inflate
) {

    private val viewModel by viewModels<TasksViewModel>()

    private var priorityAdapter: PriorityAdapter? = null

    private lateinit var selectedDate: String
    private var hasFinishedSelectingDate: Boolean = false
    private var isFirstDateTimePickerSelected: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.priorities()
        viewModel.priorities.observe(viewLifecycleOwner) {
            priorityAdapter = PriorityAdapter(it)
            priorityAdapter?.setOnItemClickListener(onRecyclerItemClickListener)
            binding.includeRecyclerPriority.recycler.apply {
                layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
                adapter = priorityAdapter
            }
        }

        binding.edStarts.setOnClickListener {
            isFirstDateTimePickerSelected = true
            dateTimePickerDialog()
        }
        binding.edEnds.setOnClickListener {
            isFirstDateTimePickerSelected = false
            dateTimePickerDialog()
        }

    }

    private fun dateTimePickerDialog() {
        val now = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog.newInstance(
            onDateSet,
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )
        childFragmentManager.let { datePickerDialog.show(it, "Datepickerdialog") };
    }

    private val onDateSet = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        selectedDate = "$dayOfMonth/$monthOfYear/$year"
        hasFinishedSelectingDate = true
        if (hasFinishedSelectingDate) {
            timePickerDialog()
        }
    }

    private fun timePickerDialog() {
        val now = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog.newInstance(
            onTimeSet,
            now.get(Calendar.HOUR_OF_DAY),
            now.get(Calendar.MINUTE),
            true
        )
        childFragmentManager.let { timePickerDialog.show(it, "Datepickerdialog") };
    }

    private val onTimeSet = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute, second ->
        val selectedTime = "$selectedDate  $hourOfDay:$minute"
        if (isFirstDateTimePickerSelected) {
            binding.edStarts.setText(selectedTime)
        } else {
            binding.edEnds.setText(selectedTime)
        }
    }

    private val onRecyclerItemClickListener = object : OnItemClickListener<String> {
        override fun onItemClick(position: Int, item: String) {

        }
    }

}