package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.SettingsViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val settingsPresentationModule = module {
    viewModel { SettingsViewModel(get(), get(), get(), get(), get()) }
}