package com.erald_guri.smartflex_android.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.erald_guri.smartflex_android.adapters.PriorityAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.data.model.TaskModel
import com.erald_guri.smartflex_android.databinding.FragmentNewTaskBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.permissions.Permission
import com.erald_guri.smartflex_android.permissions.PermissionManager
import com.erald_guri.smartflex_android.utils.isEmpty
import com.erald_guri.smartflex_android.utils.validate
import com.erald_guri.smartflex_android.view_models.CategoryViewModel
import com.erald_guri.smartflex_android.view_models.TasksViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewTasksFragment : BaseFragment<FragmentNewTaskBinding>(
    FragmentNewTaskBinding::inflate
) {

    private val viewModel by viewModels<TasksViewModel>()
    private val categoryViewModel by viewModels<CategoryViewModel>()

    private var priorityAdapter: PriorityAdapter? = null

    private lateinit var selectedDate: String
    private var hasFinishedSelectingDate: Boolean = false
    private var isFirstDateTimePickerSelected: Boolean = false
    private var selectedPriority: String = ""
    private var starts: String = ""
    private var ends: String = ""

    private val permissionManager = PermissionManager.form(this)


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

        binding.btnSave.setOnClickListener {
            if (binding.edTitle.text.isNotEmpty()) {
                createNewTask()
            } else {
                binding.edTitle.validate("Title should be empty") { s ->
                    s.isEmpty(binding.edTitle)
                }
            }
        }

    }

    override fun onFabButton(fabButton: FloatingActionButton?) {

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
        childFragmentManager.let { timePickerDialog.show(it, "Timepickerdialog") };
    }

    private val onTimeSet = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute, second ->
        val selectedTime = "$selectedDate  $hourOfDay:$minute"
        if (isFirstDateTimePickerSelected) {
            binding.edStarts.setText(selectedTime)
            starts = selectedTime
        } else {
            ends = selectedTime
            binding.edEnds.setText(selectedTime)
        }
    }
    
    private val onRecyclerItemClickListener = object : OnItemClickListener<String> {
        override fun onItemClick(position: Int, item: String) {
            selectedPriority = item
        }
    }

    private fun getCategories(): List<CategoryModel> {
        val categories = ArrayList<CategoryModel>()
        categories.clear()
        categoryViewModel.selectedAll()
        categoryViewModel.categories.observe(viewLifecycleOwner) {
            categories.addAll(it)
        }

        return categories
    }

    private fun createNewTask() {
        //TODO: error handling, loading state
        //TODO: save as draft when exit
        //TODO: permissions
        //TODO: get file from storage
        binding.apply {
            val task = TaskModel(
                edTitle.text.toString(),
                selectedPriority,
                starts,
                ends,
                edLocation.text.toString(),
                edDescription.text.toString()
            )
            viewModel.saveTask(task)
        }
    }

}