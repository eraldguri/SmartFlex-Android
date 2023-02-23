package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.databinding.LayoutCategoryItemBinding
import com.erald_guri.smartflex_android.holders.CategoryViewHolder
import com.erald_guri.smartflex_android.interfaces.OnTaskListener

class CategoryAdapter(
    private val categories: MutableList<CategoryModel>,
    private val taskListener: OnTaskListener<CategoryModel>
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutCategoryItemBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding, taskListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.onBind(category)
    }

    override fun getItemCount(): Int = categories.size

    fun addCategory(category: CategoryModel) {
        categories.add(category)
        notifyItemInserted(categories.size)
        notifyDataSetChanged()
    }

    fun removeCategory(category: CategoryModel) {
        categories.remove(category)
        notifyDataSetChanged()
    }
}