package com.example.worldwise.presentation.screens.home.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.worldwise.databinding.LayoutItemCategoryBinding
import com.example.worldwise.presentation.screens.home.models.CategoryModel
import com.example.worldwise.presentation.screens.profile.editPhoto.models.PhotoModel
import com.example.worldwise.presentation.screens.profile.editPhoto.photo.PhotoViewHolder

class CategoryAdapter(
    private val onItemClick: (CategoryModel) -> Unit
) : ListAdapter<CategoryModel, CategoryViewHolder>(CategoryModel.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = LayoutItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}