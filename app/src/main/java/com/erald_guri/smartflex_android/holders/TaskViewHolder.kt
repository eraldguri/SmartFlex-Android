package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.TaskModel
import com.erald_guri.smartflex_android.databinding.LayoutTaskCardBinding

class TaskViewHolder(private val binding: LayoutTaskCardBinding) : BaseViewHolder<TaskModel>(binding.root) {

    override fun onBind(item: TaskModel) {
        binding.apply {
            tvTitle.text = item.title
            tvDescription.text = item.description
            tvLocation.text = item.location
            tvDateTime.text = "${item.starts} - ${item.ends}"
        }
    }
}