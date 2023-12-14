package com.example.worldwise.presentation.screens.authorization.errorLogin

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.DialogErrorLoginBinding
import com.example.worldwise.presentation.common.dialog.TransparentDialogFragment

class ErrorLoginDialog : TransparentDialogFragment(R.layout.dialog_error_login) {

    private val binding by viewBinding(DialogErrorLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionOk.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
