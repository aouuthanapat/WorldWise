package com.example.worldwise.presentation.screens.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentWelcomeTranslateScreenBinding

class WelcomeTranslateScreen : Fragment(R.layout.fragment_welcome_translate_screen) {

    private val binding by viewBinding(FragmentWelcomeTranslateScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionContinue.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeTranslateScreen_to_welcomeMapScreen)
        }
    }

}