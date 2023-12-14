package com.example.worldwise.presentation.screens.profile.editPhoto.models

import com.example.worldwise.utils.DefaultDiffUtilItemCallback

data class PhotoModel(
    val photoId: Int?,
) {

    companion object {
        val diffUtil = DefaultDiffUtilItemCallback<PhotoModel> { oldItem, newItem ->
            oldItem.photoId == newItem.photoId
        }
    }
}