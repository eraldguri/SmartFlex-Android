package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.databinding.LayoutContactListItemBinding

class ContactListViewHolder(
    private val binding: LayoutContactListItemBinding
) : BaseViewHolder<ContactModel>(binding.root) {

    override fun onBind(item: ContactModel) {
        binding.apply {
            tvPhone.text = item.phone.toString()
            tvFullName.text = "${item.firstName} ${item.lastName}"
            tvTitle.text = item.title
        }
    }
}