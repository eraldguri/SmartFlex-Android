package com.erald_guri.smartflex_android.ui.settings.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    FragmentCategoryBinding::inflate
) {

    private val viewModel by viewModels<CategoryViewModel>()

    lateinit var categoryAdapter: CategoryAdapter
    private var categories = ArrayList<CategoryModel>()

    private var isEditMode: Boolean = false
    private var selectedCategory: CategoryModel? = null
    private var selectedItemPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintView.visibility = View.GONE
        binding.includeNotFound.root.visibility = View.GONE

        observe()

        binding.btnAddCategory.setOnClickListener {
            isEditMode = false
            bottomSheetDialog()
        }

        binding.includeNotFound.btnAdd.setOnClickListener { bottomSheetDialog() }
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {

    }

    private fun observe() {
        viewModel.selectedAll()
        viewModel.categories.observe(viewLifecycleOwner) {
            categories.clear()
            categories.addAll(it)
            categoryAdapter = CategoryAdapter(categories, onTaskListener)
            if (it.isEmpty()) {
                binding.includeNotFound.root.visibility = View.VISIBLE
            } else {
                binding.constraintView.visibility = View.VISIBLE
                binding.includeRecycler.recycler.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = categoryAdapter
                }
            }
        }
    }

    private val onTaskListener = object : OnTaskListener<CategoryModel> {
        override fun onItemClick(task: CategoryModel) {

        }

        override fun onEdit(position: Int, task: CategoryModel) {
            isEditMode = true
            selectedCategory = task
            selectedItemPosition = position
            bottomSheetDialog()
        }

        override fun onDelete(position: Int, task: CategoryModel) {
            viewModel.deleteCategory(task)
            viewModel.success.observe(viewLifecycleOwner) {
                if (!it.error) {
                    categoryAdapter.removeCategory(task)
                    if (categoryAdapter.itemCount == 0) {
                        binding.constraintView.visibility = View.GONE
                        binding.includeNotFound.root.visibility = View.VISIBLE
                    }
                } else {
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun bottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = DialogCategoryBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        if (isEditMode) {
            bottomSheetBinding.btnAdd.text = "Edit"

            bottomSheetBinding.edTitle.setText(selectedCategory?.title)
            bottomSheetBinding.edDescription.setText(selectedCategory?.description)
        }

        bottomSheetBinding.btnAdd.setOnClickListener {
            if (isEditMode) {
                val editedCategory = CategoryModel(bottomSheetBinding.edTitle.text.toString(), bottomSheetBinding.edDescription.text.toString())
                editedCategory.id = selectedCategory?.id
                editCategory(editedCategory, bottomSheetDialog)
            } else {
                insertCategory(bottomSheetBinding, bottomSheetDialog)
            }
        }

        bottomSheetBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun editCategory(editedCategory: CategoryModel, dialog: BottomSheetDialog) {
        viewModel.updateCategory(editedCategory)
        viewModel.success.observe(viewLifecycleOwner) {
            if (!it.error) {
                categoryAdapter.updateCategory(selectedItemPosition, editedCategory)
                dialog.dismiss()
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertCategory(bottomSheetBinding: DialogCategoryBinding, dialog: BottomSheetDialog) {
        val categoryModel = CategoryModel(bottomSheetBinding.edTitle.text.toString(), bottomSheetBinding.edDescription.text.toString())
        viewModel.insertCategory(categoryModel)
        viewModel.success.observe(viewLifecycleOwner) {
            if (!it.error) {
                categoryAdapter.addCategory(categoryModel)
                if (categoryAdapter.itemCount > 0) {
                    binding.constraintView.visibility = View.VISIBLE
                    binding.includeNotFound.root.visibility = View.GONE
                }
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}