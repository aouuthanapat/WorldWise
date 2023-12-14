package com.example.worldwise.presentation.screens.profile.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentProfileBinding
import com.example.worldwise.presentation.common.getProfilePhoto
import com.example.worldwise.presentation.screens.authorization.signUp.model.User
import com.example.worldwise.presentation.screens.profile.main.action.ActionAdapter
import com.example.worldwise.presentation.screens.profile.main.models.ProfileActionModel
import com.example.worldwise.utils.VerticalSpaceItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private var actionAdapter: ActionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionAdapter = ActionAdapter()
        setupRecyclerView()
        initUserInfo()
    }

    private fun setupRecyclerView() = with(binding) {
        val dividerItemDecoration =
            VerticalSpaceItemDecoration(
                verticalSpaceHeight = 6,
                topBackground = checkNotNull(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.translator_spinner_background
                    )
                ), bottomBackground = checkNotNull(ContextCompat.getDrawable(requireContext(), R.drawable.result_translator_background))
            )
        recyclerView.addItemDecoration(dividerItemDecoration)
        actionAdapter?.submitList(buildActionsList())
        recyclerView.adapter = actionAdapter
    }

    private fun initUserInfo() = with(binding) {
        progressBar.isVisible = true
        val user = FirebaseAuth.getInstance().currentUser?.uid
        val reference = FirebaseDatabase.getInstance().getReference("Users")

        reference.child(checkNotNull(user)).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profile = snapshot.getValue(User::class.java)
                profilePhoto.setImageResource(getProfilePhoto(profile?.photoId))
                accountName.text = "${profile?.name} ${profile?.lastName}"
                nickname.text = "@${profile?.nickName}"
                telNumber.text = profile?.number
                profilePhoto.setImageResource(getProfilePhoto(profile?.photoId))
                progressBar.isVisible = false
                recyclerView.isVisible = true
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.isVisible = false
            }
        })
    }

    private fun buildActionsList() = listOf(
        ProfileActionModel(
            action = getString(R.string.profile__action_item__confidentiality),
            onClick = {
                findNavController().navigate(R.id.action_profile_to_confDialog)
            }
        ),
        ProfileActionModel(
            action = getString(R.string.profile__action_item__help),
            onClick = {
                findNavController().navigate(R.id.action_profile_to_helpDialog)
            }
        ),
        ProfileActionModel(
            action = getString(R.string.profile__action_item__edit_phono),
            onClick = {
                findNavController().navigate(R.id.action_profile_to_editPhotoDialog)
            }
        ),
        ProfileActionModel(
            action = getString(R.string.profile__action_item__logout),
            onClick = {
                findNavController().navigate(R.id.action_profile_to_logoutDialog)
            }
        ),
    )
}