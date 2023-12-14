package com.example.worldwise.presentation.screens.profile.editPhoto

import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.DialogEditPhotoBinding
import com.example.worldwise.databinding.FragmentProfileBinding
import com.example.worldwise.presentation.common.ProfilePhoto
import com.example.worldwise.presentation.common.dialog.TransparentDialogFragment
import com.example.worldwise.presentation.common.getProfilePhoto
import com.example.worldwise.presentation.screens.authorization.signUp.model.User
import com.example.worldwise.presentation.screens.profile.editPhoto.models.PhotoModel
import com.example.worldwise.presentation.screens.profile.editPhoto.photo.PhotoAdapter
import com.example.worldwise.presentation.screens.profile.main.action.ActionAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class EditPhotoDialog : TransparentDialogFragment(R.layout.dialog_edit_photo) {

    private val binding by viewBinding(DialogEditPhotoBinding::bind)
    private var photoAdapter: PhotoAdapter? = null
    private var reference: DatabaseReference? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoAdapter = PhotoAdapter(::onItemClick)
        photoAdapter?.submitList(buildPhotoList())
        binding.photoList.adapter = photoAdapter
        binding.actionClose.setOnClickListener { findNavController().navigateUp() }
        reference = FirebaseDatabase.getInstance().getReference("Users")

        getUserData()
    }


    private fun onItemClick(item: PhotoModel) {
        reference?.child(checkNotNull(FirebaseAuth.getInstance().currentUser?.uid))
            ?.child("photoId")
            ?.setValue(item.photoId)
            ?.addOnCompleteListener { findNavController().navigateUp() }
    }

    private fun getUserData() {
        reference?.child(checkNotNull(FirebaseAuth.getInstance().currentUser?.uid))
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val profile = snapshot.getValue(User::class.java)
                    binding.profilePhoto.setImageResource(getProfilePhoto(profile?.photoId))
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }


    private fun buildPhotoList() = listOf(
        PhotoModel(ProfilePhoto.SNAKE.photoId),
        PhotoModel(ProfilePhoto.ATHLETE.photoId),
        PhotoModel(ProfilePhoto.GOTHBOY.photoId),
        PhotoModel(ProfilePhoto.ANDREADEL.photoId),
        PhotoModel(ProfilePhoto.JON.photoId),
        PhotoModel(ProfilePhoto.JESSICA.photoId),
    )
}