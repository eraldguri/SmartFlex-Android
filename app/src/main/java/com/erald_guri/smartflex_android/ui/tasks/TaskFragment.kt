package com.erald_guri.smartflex_android.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.adapters.TaskAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.TaskModel
import com.erald_guri.smartflex_android.databinding.FragmentTaskBinding
import com.erald_guri.smartflex_android.interfaces.OnTaskListener
import com.erald_guri.smartflex_android.view_models.TasksViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : BaseFragment<FragmentTaskBinding>(
    FragmentTaskBinding::inflate
) {

    private val viewModel by viewModels<TasksViewModel>()
    lateinit var taskAdapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeTasks()

        binding.btnCreateTask.setOnClickListener {
            val action = TaskFragmentDirections.actionNavTasksToNavNewTask()
            findNavController().navigate(action)
        }
    }

    private fun observeTasks() {
        viewModel.getAllTasks()
        viewModel.tasks.observe(viewLifecycleOwner) {
            val tasks = it.toMutableList()
            taskAdapter = TaskAdapter(tasks, onTaskListener)
            binding.includeRecycler.recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = taskAdapter
            }
        }
    }

    private val onTaskListener = object : OnTaskListener<TaskModel> {
        override fun onItemClick(task: TaskModel) {
            Snackbar.make(binding.root, "Item clicked", Snackbar.LENGTH_SHORT).show()
        }

        override fun onEdit(position: Int, task: TaskModel) {
            Snackbar.make(binding.root, "Item Edit", Snackbar.LENGTH_SHORT).show()
        }

        override fun onDelete(position: Int, task: TaskModel) {
            viewModel.removeTask(task)
            taskAdapter.removeItem(position)
        }

    }

}