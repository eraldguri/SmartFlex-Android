package com.erald_guri.smartflex_android.ui.settings.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.adapters.CategoryAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.databinding.DialogCategoryBinding
import com.erald_guri.smartflex_android.databinding.FragmentCategoryBinding
import com.erald_guri.smartflex_android.interfaces.OnTaskListener
import com.erald_guri.smartflex_android.view_models.CategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    FragmentCategoryBinding::inflate
) {

    private val viewModel by viewModels<CategoryViewModel>()

    lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        binding.btnAddCategory.setOnClickListener {
            bottomSheetDialog()
        }
    }

    private fun observe() {
        viewModel.selectedAll()
        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter = CategoryAdapter(it.toMutableList(), onTaskListener)
            binding.includeRecycler.recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = categoryAdapter
            }
        }
    }

    private val onTaskListener = object : OnTaskListener<CategoryModel> {
        override fun onItemClick(task: CategoryModel) {

        }

        override fun onEdit(task: CategoryModel) {

        }

        override fun onDelete(position: Int, task: CategoryModel) {
            viewModel.deleteCategory(task)
            viewModel.success.observe(viewLifecycleOwner) {
                if (it) {
                    categoryAdapter.removeCategory(task)
                } else {
                    Snackbar.make(binding.root, "An error occurred", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun bottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = DialogCategoryBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.btnAdd.setOnClickListener {
            insertCategory(bottomSheetBinding, bottomSheetDialog)
        }

        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun insertCategory(bottomSheetBinding: DialogCategoryBinding, dialog: BottomSheetDialog) {
        val categoryModel = CategoryModel(bottomSheetBinding.edTitle.text.toString(), bottomSheetBinding.edDescription.text.toString())
        viewModel.insertCategory(categoryModel)
        viewModel.success.observe(viewLifecycleOwner) {
            if (it) {
                categoryAdapter.addCategory(categoryModel)
                dialog.dismiss()
            } else {
                Snackbar.make(binding.root, "An error occurred. Please try again!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}