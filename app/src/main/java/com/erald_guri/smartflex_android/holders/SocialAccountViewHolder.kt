package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel
import com.erald_guri.smartflex_android.databinding.LayoutNewSocialLinkBinding
import com.erald_guri.smartflex_android.databinding.LayoutSocialLinkItemBinding
import com.erald_guri.smartflex_android.interfaces.OnTaskListener

class SocialAccountViewHolder(
    private val binding: LayoutSocialLinkItemBinding,
    private val onTaskListener: OnTaskListener<SocialLinkAccountModel>
) : BaseViewHolder<SocialLinkAccountModel>(binding.root) {

    override fun onBind(item: SocialLinkAccountModel) {
        binding.apply {
            tvTitle.text = item.title
            tvLink.text = item.link

            btnEdit.setOnClickListener { onTaskListener.onEdit(adapterPosition, item) }
            btnDelete.setOnClickListener { onTaskListener.onDelete(adapterPosition, item) }
        }
    }
}