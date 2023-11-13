package com.example.worldwise.presentation.screens.authorization.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentLoginBinding
import com.example.worldwise.presentation.activity.main.MainActivity
import com.example.worldwise.utils.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginScreen : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModelInputs()
        bindViewModelOutputs()
    }

    private fun bindViewModelInputs() = with(binding) {
        binding.signupText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.actionLogin.setOnClickListener {
            viewModel.onLoginClicked(editEmail.text.toString(), editPassword.text.toString())
        }
    }

    private fun bindViewModelOutputs() = with(viewModel) {
        viewModel.isSuccessLogin.bind(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
            } else {
                findNavController().navigate(R.id.action_loginFragment_to_errorLoginDialog)
            }
        }
    }
}