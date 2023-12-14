package com.example.worldwise.presentation.screens.home.adapter.news

import androidx.recyclerview.widget.RecyclerView
import com.example.worldwise.databinding.LayoutItemNewsBinding
import com.example.worldwise.presentation.screens.home.models.CategoryModel
import com.example.worldwise.presentation.screens.home.models.NewsModel

class NewsViewHolder(
    private val binding: LayoutItemNewsBinding,
    private val onClick: (NewsModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsModel) = with(binding) {
        title.text = item.Title
        author.text = item.Author
        theme.text = item.Theme
        chapter.text = item.Chapter
        rootView.setOnClickListener { onClick(item) }
    }
}