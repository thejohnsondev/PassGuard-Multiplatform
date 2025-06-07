package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.SettingsViewModel
import com.thejohnsondev.presentation.exportv.ExportPasswordsViewModel
import com.thejohnsondev.presentation.exportv.NotExportedPasswordsViewModel
import com.thejohnsondev.presentation.importv.ImportPasswordsViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val settingsPresentationModule = module {
    viewModel { SettingsViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ExportPasswordsViewModel(get(), get(), get(), get(), get()) }
    viewModel { NotExportedPasswordsViewModel(get()) }
    viewModel { ImportPasswordsViewModel(get(), get(), get(), get(), get(), get()) }
}