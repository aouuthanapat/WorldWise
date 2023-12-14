package com.example.worldwise.presentation.screens.home.models

import com.example.worldwise.utils.DefaultDiffUtilItemCallback

data class CategoryModel(
    val title: String,
    val isClicked: Boolean,
) {

    companion object {
        val diffUtil = DefaultDiffUtilItemCallback<CategoryModel> { oldItem, newItem ->
            oldItem.title == newItem.title
        }
    }
}