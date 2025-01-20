package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.additem.AddVaultItemViewModel
import com.thejohnsondev.presentation.vault.VaultViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val vaultPresentationModule = module {
    viewModel { VaultViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { AddVaultItemViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
}