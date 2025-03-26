package com.thejohnsondev.network.di

import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.Platform
import com.thejohnsondev.common.getPlatform
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.model.NoInternetConnectionException
import com.thejohnsondev.network.DemoRemoteApiImpl
import com.thejohnsondev.network.FirebaseRemoteApiImpl
import com.thejohnsondev.network.HttpClientProvider
import com.thejohnsondev.network.LogoApi
import com.thejohnsondev.network.LogoApiImpl
import com.thejohnsondev.network.RemoteApi
import dev.tmapps.konnection.Konnection
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single {
        val client = HttpClientProvider.provide().config {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
        client.plugin(HttpSend).intercept { request ->
            if (getPlatform() == Platform.IOS_SIMULATOR) {
                return@intercept execute(request)
            }
            val isInternetConnected = Konnection.instance.isConnected()
            if (!isInternetConnected) throw NoInternetConnectionException()
            execute(request)
        }
        client
    }
    when (AppType.from(BuildKonfigProvider.getAppType())) {
        AppType.DEMO -> {
            singleOf(::DemoRemoteApiImpl) { bind<RemoteApi>() }
        }
        AppType.REAL -> {
            singleOf(::FirebaseRemoteApiImpl) { bind<RemoteApi>() }
        }
    }

    singleOf(::LogoApiImpl) { bind<LogoApi>() }

    single {
        Konnection.instance
    }
    single {
        CoroutineScope(Dispatchers.IO)
    }
}