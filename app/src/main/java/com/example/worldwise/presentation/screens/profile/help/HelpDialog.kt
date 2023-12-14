package com.example.worldwise.presentation.screens.profile.help

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.DialogHelpBinding
import com.example.worldwise.databinding.FragmentWelcomeTranslateScreenBinding
import com.example.worldwise.presentation.common.dialog.TransparentDialogFragment

class HelpDialog : TransparentDialogFragment(R.layout.dialog_help) {
    private val binding by viewBinding(DialogHelpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}