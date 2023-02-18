package com.erald_guri.smartflex_android.holders

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.LayoutButtonListBinding

class ButtonViewHolder(private val binding: LayoutButtonListBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ButtonModel) {
        binding.root.apply {
            text = item.text
            setCompoundDrawablesWithIntrinsicBounds(item.icon, null, null, null)
            compoundDrawablePadding = 16
//            setBackgroundColor(Color.parseColor(item.color))
            setBackgroundResource(R.drawable.button_round_corners)
            background.colorFilter = PorterDuffColorFilter(Color.parseColor(item.color), PorterDuff.Mode.SRC_IN)

            //TODO: change stroke color of drawable
            if (adapterPosition == 0) {
                val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.button_round_corners)
                val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                DrawableCompat.setTint(wrappedDrawable, Color.RED)
            }


//            item.icon?.colorFilter = PorterDuffColorFilter(Color.parseColor(item.color), PorterDuff.Mode.SRC_IN)

        }
    }

}