package com.erald_guri.smartflex_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erald_guri.smartflex_android.data.model.CategoryModel
import com.erald_guri.smartflex_android.databinding.LayoutCategoryItemBinding
import com.erald_guri.smartflex_android.holders.CategoryViewHolder
import com.erald_guri.smartflex_android.interfaces.OnTaskListener

class CategoryAdapter(
    private val categories: ArrayList<CategoryModel>,
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addCategory(category: CategoryModel) {
        if (!categories.contains(category)) {
            categories.add(category)
            notifyDataSetChanged()
        }
    }

    fun updateCategory(position: Int, category: CategoryModel) {
        categories[position].title = category.title
        categories[position].description = category.description
        notifyDataSetChanged()
    }

    fun removeCategory(category: CategoryModel) {
        categories.remove(category)
        notifyDataSetChanged()
    }
}