package com.thejohnsondev.network.di

import com.thejohnsondev.model.NoInternetConnectionException
import com.thejohnsondev.network.HttpClientProvider
import com.thejohnsondev.network.RemoteApi
import com.thejohnsondev.network.RemoteApiImpl
import com.thejohnsondev.network.interceptors.AuthTokenInterceptor
import com.thejohnsondev.network.interceptors.AuthTokenInterceptorImpl
import dev.tmapps.konnection.Konnection
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
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
                })
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
        client.plugin(HttpSend).intercept { request ->
            val isInternetConnected = Konnection.instance.isConnected()
            if (!isInternetConnected) throw NoInternetConnectionException()
            execute(request)
        }
        client.plugin(HttpSend).intercept { request ->
            val interceptedRequest = get<AuthTokenInterceptor>().addAuthHeader(request)
            execute(interceptedRequest)
        }
        client
    }
    singleOf(::AuthTokenInterceptorImpl) { bind<AuthTokenInterceptor>() }
    singleOf(::RemoteApiImpl) { bind<RemoteApi>() }
    single {
        Konnection.instance
    }
}