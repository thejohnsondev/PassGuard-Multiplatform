package com.thejohnsondev.ui.di

import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import org.koin.core.module.Module
import org.koin.dsl.module

actual val uiModule: Module = module {
    single { DeviceThemeConfig(get()) }
}