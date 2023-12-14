package com.example.worldwise.presentation.screens.profile.main.action

import androidx.recyclerview.widget.RecyclerView
import com.example.worldwise.databinding.ItemProfileActionBinding
import com.example.worldwise.presentation.screens.profile.main.models.ProfileActionModel

class ActionViewHolder(
    private val binding: ItemProfileActionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProfileActionModel) = with(binding) {
        action.text = item.action
        rootView.setOnClickListener { item.onClick() }
    }
}