package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.signup.SignUpViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val authPresentationModule = module {
    viewModel { SignUpViewModel(get(), get(), get()) }
}