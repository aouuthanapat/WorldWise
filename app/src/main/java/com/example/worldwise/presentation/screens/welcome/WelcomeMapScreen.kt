package com.example.worldwise.presentation.screens.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentWelcomeMapScreenBinding
import com.example.worldwise.presentation.activity.auth.AuthActivity


class WelcomeMapScreen : Fragment(R.layout.fragment_welcome_map_screen) {

    private val binding by viewBinding(FragmentWelcomeMapScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionContinue.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
    }


}