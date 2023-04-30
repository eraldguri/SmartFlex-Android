package com.erald_guri.smartflex_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.databinding.LayoutSpinnerItemBinding
import com.erald_guri.smartflex_android.ui.tasks.SpinnerItem

class FlexSpinnerAdapter(val ctx: Context, val items: List<SpinnerItem>)
    : ArrayAdapter<SpinnerItem>(ctx, 0, items) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = LayoutSpinnerItemBinding.inflate(layoutInflater, parent, false)

        val item = items[position]

        binding.apply {
            constraintLayout.setBackgroundColor(item.color)
            tvText.text = item.text
        }

        return binding.root
    }

}