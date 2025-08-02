package com.thejohnsondev.network.di

import com.thejohnsondev.network.logo.DemoLogoApiImpl
import com.thejohnsondev.network.vault.DemoRemoteApiImpl
import com.thejohnsondev.network.logo.LogoApi
import com.thejohnsondev.network.vault.RemoteApi
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val demoApiModule = module {
    singleOf(::DemoRemoteApiImpl) { bind<RemoteApi>() }
    singleOf(::DemoLogoApiImpl) { bind<LogoApi>() }
}