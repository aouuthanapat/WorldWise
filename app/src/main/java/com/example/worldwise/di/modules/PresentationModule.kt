package com.example.worldwise.di.modules

import com.example.worldwise.presentation.screens.authorization.login.LoginViewModel
import com.example.worldwise.presentation.screens.authorization.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        SignUpViewModel()
    }

    viewModel {
        LoginViewModel()
    }

}
