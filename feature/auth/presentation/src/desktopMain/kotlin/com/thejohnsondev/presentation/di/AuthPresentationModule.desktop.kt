package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.SignUpViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val authPresentationModule = module {
    viewModel { SignUpViewModel() }
}