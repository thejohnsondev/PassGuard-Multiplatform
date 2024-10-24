package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.signup.SignUpViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val authPresentationModule = module {
    viewModelOf(::SignUpViewModel)
}