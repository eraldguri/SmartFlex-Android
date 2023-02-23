package com.erald_guri.smartflex_android.dialogs

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.databinding.DialogCategoryBinding
import com.erald_guri.smartflex_android.ui.settings.fragments.CategoryFragment
import com.erald_guri.smartflex_android.view_models.CategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CategoryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)

        binding.btnAdd.setOnClickListener {
            insertCategory()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun insertCategory() {
        val categoryModel = CategoryModel(binding.edTitle.text.toString(), binding.edDescription.text.toString())
        viewModel.insertCategory(categoryModel)
        viewModel.success.observe(viewLifecycleOwner) {
            if (it) {
                dialog?.dismiss()
            } else {
                Snackbar.make(binding.root, "An error occurred. Please try again!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}