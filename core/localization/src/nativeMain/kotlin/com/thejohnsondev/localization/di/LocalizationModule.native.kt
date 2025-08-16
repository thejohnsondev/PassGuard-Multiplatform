package com.thejohnsondev.localization.di

import com.thejohnsondev.localization.AppLocaleManager
import org.koin.dsl.module

actual val appLocaleManagerModule = module {
    single { AppLocaleManager() }
}