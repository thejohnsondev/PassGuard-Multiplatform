package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.SettingsViewModel
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModelOf

actual val settingsPresentationModule = module {
    viewModelOf(::SettingsViewModel)
}