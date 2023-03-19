package com.erald_guri.smartflex_android.holders

import android.content.Context
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.databinding.LayoutContactListItemBinding

class ContactListViewHolder(
    private val context: Context,
    private val binding: LayoutContactListItemBinding
) : BaseViewHolder<ContactModel>(binding.root) {

    override fun onBind(item: ContactModel) {
        binding.apply {
            tvPhone.text = item.phone
            tvFullName.text = "${item.firstName} ${item.lastName}"
            tvTitle.text = item.title
            Glide.with(context)
                .load(item.photoPath)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_baseline_emoji_emotions_24))
                .into(imageView)
        }
    }
}