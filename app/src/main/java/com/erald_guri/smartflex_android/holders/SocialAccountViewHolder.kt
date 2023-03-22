package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.SocialLinkAccountModel
import com.erald_guri.smartflex_android.databinding.LayoutNewSocialLinkBinding
import com.erald_guri.smartflex_android.databinding.LayoutSocialLinkItemBinding

class SocialAccountViewHolder(
    private val binding: LayoutSocialLinkItemBinding
) : BaseViewHolder<SocialLinkAccountModel>(binding.root) {

    override fun onBind(item: SocialLinkAccountModel) {
        binding.apply {
            tvTitle.text = item.title
            tvLink.text = item.link
        }
    }
}