package com.thejohnsondev.common.di

import com.thejohnsondev.common.utils.BiometricsProvider
import org.koin.dsl.module

actual val commonModule = module {
    single {
        BiometricsProvider(get())
    }
}