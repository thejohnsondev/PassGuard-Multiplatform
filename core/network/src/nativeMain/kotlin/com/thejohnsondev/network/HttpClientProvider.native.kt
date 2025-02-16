package com.thejohnsondev.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual object HttpClientProvider {
    actual fun provide(): HttpClient = HttpClient(Darwin) {
        engine {
            configureRequest {
                setTimeoutInterval(NetworkEngineConfig.default.connectTimeout.toDouble())
            }
        }
    }
}