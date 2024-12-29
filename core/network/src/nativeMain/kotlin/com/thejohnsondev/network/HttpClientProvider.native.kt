package com.thejohnsondev.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE

actual object HttpClientProvider {
    actual fun provide(): HttpClient = HttpClient(Darwin) {
        engine {
            configureRequest {
                // TODO configure request here
            }
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}