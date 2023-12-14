package com.example.worldwise.presentation.screens.authorization.signUp

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.worldwise.R
import com.example.worldwise.databinding.FragmentSignUpBinding
import com.example.worldwise.presentation.activity.main.MainActivity
import com.example.worldwise.utils.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpScreen : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModelInputs()
        bindViewModelOutputs()
    }

    private fun bindViewModelInputs() = with(binding) {
        loginText.setOnClickListener {
            findNavController().navigateUp()
        }

        actionSignup.setOnClickListener {
            viewModel.validateInputs(
                name = editName.text.toString(),
                lastName = editSurname.text.toString(),
                email = editEmail.text.toString(),
                phone = editNumber.text.toString(),
                nickName = editNickname.text.toString(),
                password = editPassword.text.toString(),
                isCheckedPrivacy = privacyRadioAction.isChecked
            )
        }
    }

    private fun bindViewModelOutputs() = with(viewModel) {
        isValidName.bind(viewLifecycleOwner) {
            binding.errorName.isVisible = !it
        }

        isValidSurname.bind(viewLifecycleOwner) {
            binding.errorSurname.isVisible = !it
        }

        isValidNumber.bind(viewLifecycleOwner) {
            binding.errorNumber.isVisible = !it
        }

        isValidEmail.bind(viewLifecycleOwner) {
            binding.errorEmail.isVisible = !it
        }

        isValidNickName.bind(viewLifecycleOwner) {
            binding.errorNickname.isVisible = !it
        }

        isValidPassword.bind(viewLifecycleOwner) {
            binding.errorPassword.isVisible = !it
        }

        isCheckedPrivacy.bind(viewLifecycleOwner) {
            if (it) {
                binding.privacyInfoFirst.setTextColor(requireContext().getColor(R.color.colorSecondary))
                binding.privacyInfoSecond.setTextColor(requireContext().getColor(R.color.colorSecondary))
                binding.privacyInfoThird.setTextColor(requireContext().getColor(R.color.colorSecondary))
                binding.privacyInfoFirst.setTextColor(requireContext().getColor(R.color.colorSecondary))
                binding.privacyRadioAction.highlightColor =
                    requireContext().getColor(R.color.colorSecondary)
            } else {
                binding.privacyRadioAction.highlightColor =
                    requireContext().getColor(R.color.errorText)
                binding.privacyInfoFirst.setTextColor(requireContext().getColor(R.color.errorText))
                binding.privacyInfoSecond.setTextColor(requireContext().getColor(R.color.errorText))
                binding.privacyInfoThird.setTextColor(requireContext().getColor(R.color.errorText))
            }
        }

        successValidations.bind(viewLifecycleOwner) {
            if (it) {
                viewModel.registerUser(
                    name = binding.editName.text.toString(),
                    lastName = binding.editSurname.text.toString(),
                    email = binding.editEmail.text.toString(),
                    phone = binding.editNumber.text.toString(),
                    nickName = binding.editNickname.text.toString(),
                    password = binding.editPassword.text.toString()
                )
            }
        }

        isSuccessSignUp.bind(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
            } else {
                val error = getString(R.string.error_something_went_wrong)
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
