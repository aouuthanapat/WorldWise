package com.example.worldwise.presentation.screens.home.adapter.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.worldwise.databinding.LayoutItemNewsBinding
import com.example.worldwise.presentation.screens.home.models.NewsModel

class NewsAdapter(
    private val onItemClick: (NewsModel) -> Unit
) : ListAdapter<NewsModel, NewsViewHolder>(NewsModel.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = LayoutItemNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewsViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}