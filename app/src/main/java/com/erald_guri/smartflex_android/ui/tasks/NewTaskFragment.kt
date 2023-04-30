package com.erald_guri.smartflex_android.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import dagger.hilt.android.AndroidEntryPoint

import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.FlexSpinnerAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentNewTaskBinding
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton

@AndroidEntryPoint
class NewTaskFragment : BaseFragment<FragmentNewTaskBinding>(FragmentNewTaskBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinners()

        binding.btnTag.setOnClickListener {
            if (binding.edTags.text.toString().isNotEmpty()) {
                addChip(binding.edTags.text.toString())
                binding.edTags.setText("")
            }
        }
    }

    private fun addChip(submitedText: String) {
        val chip = Chip(requireContext())
        chip.apply {
            text = submitedText
            isCloseIconVisible = true
            setChipIconResource(R.drawable.ic_baseline_close_24)
            setOnCloseIconClickListener {
                binding.chipGroup.removeView(chip)
            }

            binding.chipGroup.addView(chip)
        }
    }

    private fun initSpinners() {
        val priorities: MutableList<SpinnerItem> = mutableListOf()
        priorities.add(SpinnerItem(ContextCompat.getColor(requireContext(), R.color.color_orange), "Low"))
        priorities.add(SpinnerItem(ContextCompat.getColor(requireContext(), R.color.color_green), "Medium"))
        priorities.add(SpinnerItem(ContextCompat.getColor(requireContext(), R.color.color_red), "High"))
        priorities.add(SpinnerItem(ContextCompat.getColor(requireContext(), R.color.color_red2), "Urgent"))
        val priorityAdapter = FlexSpinnerAdapter(requireContext(), priorities)
        binding.spPriority.apply {
            adapter = priorityAdapter
            onItemSelectedListener = onPrioritySelectedListener
        }
    }

    private val onPrioritySelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }


}