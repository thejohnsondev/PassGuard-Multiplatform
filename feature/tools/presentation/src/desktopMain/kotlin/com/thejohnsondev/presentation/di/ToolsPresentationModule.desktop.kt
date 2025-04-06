package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val toolsPresentationModule = module {
    viewModel { PasswordGeneratorViewModel(get()) }
}