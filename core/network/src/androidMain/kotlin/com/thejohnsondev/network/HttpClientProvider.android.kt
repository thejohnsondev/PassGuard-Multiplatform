package com.thejohnsondev.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import java.time.Duration

actual object HttpClientProvider {
    actual fun provide(): HttpClient = HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(NetworkEngineConfig.default.followRedirects)
                connectTimeout(Duration.ofMillis(NetworkEngineConfig.default.connectTimeout))
                readTimeout(Duration.ofMillis(NetworkEngineConfig.default.connectionRequestTimeout))
            }
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY
        }
        expectSuccess = true
    }
}