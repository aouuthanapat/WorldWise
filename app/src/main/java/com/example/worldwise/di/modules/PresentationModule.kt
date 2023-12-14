package com.example.worldwise.di.modules

import com.example.worldwise.presentation.screens.authorization.login.LoginViewModel
import com.example.worldwise.presentation.screens.authorization.signUp.SignUpViewModel
import com.example.worldwise.presentation.screens.home.HomeViewModel
import com.example.worldwise.presentation.screens.map.MapViewModel
import com.example.worldwise.presentation.screens.translator.TranslatorViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        SignUpViewModel()
    }

    viewModel {
        LoginViewModel()
    }

    viewModel {
        HomeViewModel(application = androidApplication())
    }

    viewModel {
        MapViewModel(
            application = androidApplication(),
            googleMapRepository = get(),
        )
    }
    viewModel {
        TranslatorViewModel(application = androidApplication())
    }
}
