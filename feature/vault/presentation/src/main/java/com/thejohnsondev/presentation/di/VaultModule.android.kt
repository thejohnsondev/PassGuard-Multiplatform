package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.vault.VaultViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val vaultPresentationModule = module {
    viewModelOf(::VaultViewModel)
}