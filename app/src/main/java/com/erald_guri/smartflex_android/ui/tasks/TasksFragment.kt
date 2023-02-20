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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : BaseFragment<FragmentTasksBinding>(
    FragmentTasksBinding::inflate
) {

    private val viewModel by viewModels<TasksViewModel>()

    private var priorityAdapter: PriorityAdapter? = null

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

    }

    private val onRecyclerItemClickListener = object : OnItemClickListener<String> {
        override fun onItemClick(position: Int, item: String) {

        }
    }

}