package com.erald_guri.smartflex_android.ui.tasks

import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentTasksBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : BaseFragment<FragmentTasksBinding>(
    FragmentTasksBinding::inflate
) {

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.setOnClickListener {
            val action = TasksFragmentDirections.actionNavTasksToNewTaskFragment()
            findNavController().navigate(action)
        }
    }

    override fun onToolbar(toolbar: Toolbar?) {
    }

}