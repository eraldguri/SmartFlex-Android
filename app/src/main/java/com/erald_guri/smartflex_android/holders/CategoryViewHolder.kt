package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.databinding.LayoutCategoryItemBinding
import com.erald_guri.smartflex_android.interfaces.OnTaskListener

class CategoryViewHolder(
    private val binding: LayoutCategoryItemBinding,
    private val taskListener: OnTaskListener<CategoryModel>
) : BaseViewHolder<CategoryModel>(binding.root) {

    override fun onBind(item: CategoryModel) {
        binding.apply {
            tvTitle.text = item.title
            tvDescription.text = item.description

            root.setOnClickListener { taskListener.onItemClick(item) }
            btnEdit.setOnClickListener { taskListener.onItemClick(item) }
            btnDelete.setOnClickListener { taskListener.onDelete(adapterPosition, item) }
        }
    }
}