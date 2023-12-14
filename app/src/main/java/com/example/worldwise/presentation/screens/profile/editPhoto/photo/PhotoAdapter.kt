package com.example.worldwise.presentation.screens.profile.editPhoto.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.worldwise.databinding.LayoutItemCategoryBinding
import com.example.worldwise.databinding.LayoutItemPhotoBinding
import com.example.worldwise.presentation.screens.profile.editPhoto.models.PhotoModel

class PhotoAdapter(
    private val onItemClick: (PhotoModel) -> Unit
) : ListAdapter<PhotoModel, PhotoViewHolder>(PhotoModel.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = LayoutItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PhotoViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}