package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorViewModel
import com.thejohnsondev.presentation.vaulthealth.VaultHealthViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val toolsPresentationModule = module {
    viewModel { PasswordGeneratorViewModel(get(), get(), get(), get()) }
    viewModel { VaultHealthViewModel(get(), get(), get()) }
}