package com.thejohnsondev.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache5.Apache5

actual object HttpClientProvider {
    actual fun provide(): HttpClient = HttpClient(Apache5) {
        engine {
            followRedirects = NetworkEngineConfig.default.followRedirects
            connectTimeout = NetworkEngineConfig.default.connectTimeout
            connectionRequestTimeout = NetworkEngineConfig.default.connectionRequestTimeout
        }
    }
}