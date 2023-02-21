package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.TaskModel
import com.erald_guri.smartflex_android.databinding.LayoutTaskCardBinding
import com.erald_guri.smartflex_android.holders.TaskViewHolder

class TaskAdapter(
    private val tasks: List<TaskModel>
) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutTaskCardBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.onBind(task)
    }

    override fun getItemCount(): Int = tasks.size
}