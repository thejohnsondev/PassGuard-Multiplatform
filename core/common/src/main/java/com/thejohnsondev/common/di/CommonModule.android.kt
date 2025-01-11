package com.thejohnsondev.common.di

import com.thejohnsondev.common.utils.BiometricsProvider
import com.thejohnsondev.model.auth.firebase.FBApiKey
import org.koin.dsl.module
import org.thejohnsondev.common.BuildKonfig

actual val commonModule = module {
    single {
        BiometricsProvider(get())
    }
    single {
        FBApiKey(BuildKonfig.FIREBASE_API_KEY)
    }
}