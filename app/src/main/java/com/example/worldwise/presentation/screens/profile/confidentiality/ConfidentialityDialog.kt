package com.example.worldwise.presentation.screens.profile.confidentiality

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.DialogConfidentialityBinding
import com.example.worldwise.presentation.common.dialog.TransparentDialogFragment

class ConfidentialityDialog : TransparentDialogFragment(R.layout.dialog_confidentiality) {

    private val binding by viewBinding(DialogConfidentialityBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}