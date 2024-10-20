package com.thejohnsondev.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache5.Apache5
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE

actual object HttpClientProvider {
    actual fun provide(): HttpClient = HttpClient(Apache5) {
        engine {
            followRedirects = NetworkEngineConfig.default.followRedirects
            connectTimeout = NetworkEngineConfig.default.connectTimeout
            connectionRequestTimeout = NetworkEngineConfig.default.connectionRequestTimeout
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY
        }
        expectSuccess = true
    }
}