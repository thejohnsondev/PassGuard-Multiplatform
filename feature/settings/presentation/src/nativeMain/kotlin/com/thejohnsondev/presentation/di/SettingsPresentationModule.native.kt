package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.SettingsViewModel
import com.thejohnsondev.presentation.export.ExportPasswordsViewModel
import com.thejohnsondev.presentation.export.NotExportedPasswordsViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val settingsPresentationModule = module {
    viewModel { SettingsViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ExportPasswordsViewModel(get(), get(), get(), get(), get()) }
    viewModel { NotExportedPasswordsViewModel(get()) }
}