package com.erald_guri.smartflex_android.holders

import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.databinding.LayoutContactListItemBinding
import com.erald_guri.smartflex_android.interfaces.OnContactListener

class ContactListViewHolder(
    private val context: Context,
    private val binding: LayoutContactListItemBinding,
    private val onContactListener: OnContactListener
) : BaseViewHolder<ContactModel>(binding.root) {

    override fun onBind(item: ContactModel) {
        binding.apply {
            tvFullName.text = "${item.firstName} ${item.lastName}"
            tvTitle.text = item.title

            val imageUri = Uri.parse(item.photoPath)
            imageView.imageTintMode = null
            if (item.photoPath.isNotEmpty()) {
                Glide.with(context)
                    .load("file:" + imageUri)
                    .override(500, 500)
                    .into(imageView)
            } else {
                Glide.with(context)
                    .load(ContextCompat.getDrawable(context, R.drawable.ic_baseline_emoji_emotions_24))
                    .override(500, 500)
                    .into(imageView)
            }

            root.setOnClickListener { onContactListener.onDetailView(item.id) }
            btnEdit.setOnClickListener { onContactListener.onEditContact(item.id) }
            btnFavorite.setOnClickListener { onContactListener.onFavorite() }
            btnCall.setOnClickListener { onContactListener.onCall(item.phone) }
            btnMessage.setOnClickListener { onContactListener.onMessage() }
        }
    }
}