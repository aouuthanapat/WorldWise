package com.example.worldwise.presentation.screens.profile.main.models

import com.example.worldwise.utils.DefaultDiffUtilItemCallback

data class ProfileActionModel (
    val action: String,
    val onClick: () -> Unit,
) {
    companion object {
        val diffUtil = DefaultDiffUtilItemCallback<ProfileActionModel> { oldItem, newItem ->
            oldItem.action == newItem.action
        }
    }
}