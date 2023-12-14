package com.example.worldwise.presentation.screens.profile.editPhoto.photo

import androidx.recyclerview.widget.RecyclerView
import com.example.worldwise.databinding.LayoutItemPhotoBinding
import com.example.worldwise.presentation.common.getProfilePhoto
import com.example.worldwise.presentation.screens.profile.editPhoto.models.PhotoModel

class PhotoViewHolder(
    private val binding: LayoutItemPhotoBinding,
    private val onItemClick: (PhotoModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PhotoModel) = with(binding) {
        photo.setImageResource(getProfilePhoto(item.photoId))
        binding.rootView.setOnClickListener { onItemClick(item) }
    }
}