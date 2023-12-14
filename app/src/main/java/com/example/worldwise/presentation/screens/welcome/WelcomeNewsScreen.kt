package com.example.worldwise.presentation.screens.welcome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentWelcomeNewsScreenBinding
import com.example.worldwise.presentation.activity.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth

class WelcomeNewsScreen : Fragment(R.layout.fragment_welcome_news_screen) {

    private val binding by viewBinding(FragmentWelcomeNewsScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionContinue.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeNewsScreen_to_welcomeTranslateScreen)
        }
        binding.welcomeSkip.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }

}