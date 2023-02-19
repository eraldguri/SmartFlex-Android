package com.erald_guri.smartflex_android.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.adapters.ButtonAdapter
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.FragmentTasksBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.view_models.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : BaseFragment<FragmentTasksBinding>(
    FragmentTasksBinding::inflate
) {

    private val viewModel by viewModels<TasksViewModel>()

    private var buttonAdapter: ButtonAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.categories(false)
        viewModel.categories.observe(viewLifecycleOwner) {
            buttonAdapter = ButtonAdapter(it, false)
            buttonAdapter?.setOnItemClickListener(onRecyclerItemClickListener)
            binding.includeRecyclerCategory.recycler.apply {
                layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                adapter = buttonAdapter
            }
        }

    }

    private val onRecyclerItemClickListener = object : OnItemClickListener<ButtonModel> {
        override fun onItemClick(position: Int, item: ButtonModel) {
            val lastItemPosition = buttonAdapter?.itemCount?.minus(1)
            if (lastItemPosition == position) {
                val action = TasksFragmentDirections.actionNavTasksToCategoryDialogFragment()
                findNavController().navigate(action)
            }
        }
    }

}