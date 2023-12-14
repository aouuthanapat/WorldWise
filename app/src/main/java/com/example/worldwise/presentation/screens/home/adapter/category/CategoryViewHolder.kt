package com.example.worldwise.presentation.screens.home.adapter.category

import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwise.R
import com.example.worldwise.databinding.LayoutItemCategoryBinding
import com.example.worldwise.presentation.screens.home.models.CategoryModel
import com.example.worldwise.presentation.screens.profile.editPhoto.models.PhotoModel

class CategoryViewHolder(
    private val binding: LayoutItemCategoryBinding,
    private val onItemClick : (CategoryModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryModel) = with(binding) {
        category.text = item.title
        if (item.isClicked) {
            category.backgroundTintList = ColorStateList.valueOf(root.context.getColor(R.color.colorSecondary))
            category.setTextColor(root.context.getColor(R.color.colorPrimary))
        } else {
            category.backgroundTintList = ColorStateList.valueOf(root.context.getColor(R.color.colorTertiary))
            category.setTextColor(root.context.getColor(R.color.colorSecondary))
        }

        rootView.setOnClickListener { onItemClick(item) }
    }
}