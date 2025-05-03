package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorViewModel
import com.thejohnsondev.presentation.vaulthealth.VaultHealthViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val toolsPresentationModule = module {
    viewModelOf(::PasswordGeneratorViewModel)
    viewModelOf(::VaultHealthViewModel)
}