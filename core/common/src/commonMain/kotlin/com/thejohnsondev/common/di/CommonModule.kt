package com.thejohnsondev.common.di

import com.thejohnsondev.common.model.auth.firebase.FBApiKey
import com.thejohnsondev.common.model.auth.logo.LogoApiKey
import org.koin.dsl.module
import org.thejohnsondev.common.BuildKonfig

val commonModule = module {
    single {
        FBApiKey(BuildKonfig.FIREBASE_API_KEY)
    }
    single {
        LogoApiKey(BuildKonfig.LOGO_API_KEY)
    }
}