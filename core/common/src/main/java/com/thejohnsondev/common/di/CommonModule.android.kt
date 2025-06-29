package com.thejohnsondev.common.di

import com.thejohnsondev.model.auth.firebase.FBApiKey
import com.thejohnsondev.model.auth.logo.LogoApiKey
import org.koin.dsl.module
import org.thejohnsondev.common.BuildKonfig

actual val commonModule = module {
    single {
        FBApiKey(BuildKonfig.FIREBASE_API_KEY)
    }
    single {
        LogoApiKey(BuildKonfig.LOGO_API_KEY)
    }
}