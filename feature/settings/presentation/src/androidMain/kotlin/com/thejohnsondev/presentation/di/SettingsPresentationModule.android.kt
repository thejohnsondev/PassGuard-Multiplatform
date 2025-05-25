package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.SettingsViewModel
import com.thejohnsondev.presentation.export.ExportPasswordsViewModel
import com.thejohnsondev.presentation.export.NotExportedPasswordsViewModel
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModelOf

actual val settingsPresentationModule = module {
    viewModelOf(::SettingsViewModel)
    viewModelOf(::ExportPasswordsViewModel)
    viewModelOf(::NotExportedPasswordsViewModel)
}