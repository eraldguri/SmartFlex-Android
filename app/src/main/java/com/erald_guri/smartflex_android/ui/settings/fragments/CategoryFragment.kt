package com.erald_guri.smartflex_android.ui.settings.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentCategoryBinding

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    FragmentCategoryBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddCategory.setOnClickListener {
            val action = CategoryFragmentDirections.actionCategoryFragmentToCategoryDialogFragment()
            findNavController().navigate(action)
        }
    }

}