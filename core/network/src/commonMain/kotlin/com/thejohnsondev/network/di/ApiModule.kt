package com.thejohnsondev.network.di

import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.network.vault.DemoRemoteApiImpl
import com.thejohnsondev.network.vault.FirebaseRemoteApiImpl
import com.thejohnsondev.network.logo.LogoApi
import com.thejohnsondev.network.logo.LogoApiImpl
import com.thejohnsondev.network.vault.RemoteApi
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val apiModule = module {
    when (AppType.from(BuildKonfigProvider.getAppType())) {
        AppType.DEMO -> {
            singleOf(::DemoRemoteApiImpl) { bind<RemoteApi>() }
        }
        AppType.DEV,
        AppType.PROD -> {
            singleOf(::FirebaseRemoteApiImpl) { bind<RemoteApi>() }
        }
    }

    singleOf(::LogoApiImpl) { bind<LogoApi>() }
}