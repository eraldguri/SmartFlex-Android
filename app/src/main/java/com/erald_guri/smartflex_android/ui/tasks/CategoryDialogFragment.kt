package com.erald_guri.smartflex_android.ui.tasks

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.erald_guri.smartflex_android.adapters.ButtonAdapter
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.TaskCategoryDialogBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.view_models.TasksViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDialogFragment : BottomSheetDialogFragment() {

    private var _binding: TaskCategoryDialogBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<TasksViewModel>()
    private var buttonAdapter: ButtonAdapter? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = TaskCategoryDialogBinding.inflate(layoutInflater)
        val root = binding?.root


        viewModel.categories(true)
        viewModel.categories.observe(viewLifecycleOwner) {
            buttonAdapter = ButtonAdapter(it, true)
            buttonAdapter?.setOnItemClickListener(onRecyclerItemClickListener)
            binding?.includeRecyclerCategory!!.recycler.apply {
                layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                adapter = buttonAdapter
            }
        }

        return root
    }

    private val onRecyclerItemClickListener = object : OnItemClickListener<ButtonModel> {
        override fun onItemClick(position: Int, item: ButtonModel) {

        }
    }
}