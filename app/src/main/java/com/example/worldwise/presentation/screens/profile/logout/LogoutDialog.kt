package com.example.worldwise.presentation.screens.profile.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.DialogLogoutBinding
import com.example.worldwise.presentation.activity.auth.AuthActivity
import com.example.worldwise.presentation.activity.welcome.WelcomeActivity
import com.example.worldwise.presentation.common.dialog.TransparentDialogFragment
import com.google.firebase.auth.FirebaseAuth

class LogoutDialog : TransparentDialogFragment(R.layout.dialog_logout) {

    private val binding by viewBinding(DialogLogoutBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }

        binding.actionCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}