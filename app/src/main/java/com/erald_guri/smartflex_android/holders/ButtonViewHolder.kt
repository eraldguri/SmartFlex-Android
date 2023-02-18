package com.erald_guri.smartflex_android.holders

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.interfaces.OnRecyclerItemClickListener

class ButtonViewHolder(
    private val binding: LayoutButtonListBinding,
    private val listener: OnItemClickListener
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ButtonModel) {
        binding.root.apply {
            text = item.text
            setCompoundDrawablesWithIntrinsicBounds(item.icon, null, null, null)
            compoundDrawablePadding = 16

            binding.root.setBackgroundResource(R.drawable.button_round_selected)
            val backgroundDrawable = binding.root.background
            if (backgroundDrawable is ShapeDrawable) {
                backgroundDrawable.paint.color = Color.parseColor(item.color)
            } else if (backgroundDrawable is GradientDrawable) {
               backgroundDrawable.setColor(Color.parseColor(item.color))
            }

        }
    }

    fun setSelectedState(item: ButtonModel) {
        binding.root.setBackgroundResource(R.drawable.button_round_corners_unselected)
        val backgroundDrawable = binding.root.background
        if (backgroundDrawable is ShapeDrawable) {
            backgroundDrawable.paint.color = Color.parseColor(item.color)
        } else if (backgroundDrawable is GradientDrawable) {
            backgroundDrawable.setColor(Color.parseColor(item.color))
        }
    }

    fun setUnSelectedState(item: ButtonModel) {
        binding.root.setBackgroundResource(R.drawable.button_round_selected)
        val backgroundDrawable = binding.root.background
        if (backgroundDrawable is ShapeDrawable) {
            backgroundDrawable.paint.color = Color.parseColor(item.color)
        } else if (backgroundDrawable is GradientDrawable) {
            backgroundDrawable.setColor(Color.parseColor(item.color))
        }
    }

}