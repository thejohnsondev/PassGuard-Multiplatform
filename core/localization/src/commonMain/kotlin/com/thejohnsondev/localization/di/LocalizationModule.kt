package com.thejohnsondev.localization.di

import com.thejohnsondev.localization.LocalizationUtils
import org.koin.core.module.Module
import org.koin.dsl.module

expect val appLocaleManagerModule: Module

val localizationModule = module {
    single { LocalizationUtils(get(), get()) }
}
