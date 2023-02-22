package com.erald_guri.smartflex_android.holders

import com.erald_guri.smartflex_android.base.BaseViewHolder
import com.erald_guri.smartflex_android.data.model.TaskModel
import com.erald_guri.smartflex_android.databinding.LayoutTaskCardBinding
import com.erald_guri.smartflex_android.interfaces.OnTaskListener

class TaskViewHolder(
    private val binding: LayoutTaskCardBinding,
    private val onTaskListener: OnTaskListener
) : BaseViewHolder<TaskModel>(binding.root) {

    override fun onBind(item: TaskModel) {
        binding.apply {
            tvTitle.text = item.title
            tvDescription.text = item.description
            tvLocation.text = item.location
            tvDateTime.text = "${item.starts} - ${item.ends}"
        }

        binding.root.setOnClickListener {
            onTaskListener.onItemClick(item)
        }

        binding.btnEdit.setOnClickListener {
            onTaskListener.onEdit(item)
        }

        binding.btnDelete.setOnClickListener {
            onTaskListener.onDelete(adapterPosition, item)
        }
    }
}