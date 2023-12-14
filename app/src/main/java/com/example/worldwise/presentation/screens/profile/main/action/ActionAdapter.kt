package com.example.worldwise.presentation.screens.profile.main.action

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.worldwise.databinding.ItemProfileActionBinding
import com.example.worldwise.presentation.screens.profile.main.models.ProfileActionModel

class ActionAdapter : ListAdapter<ProfileActionModel, ActionViewHolder>(ProfileActionModel.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val binding = ItemProfileActionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}