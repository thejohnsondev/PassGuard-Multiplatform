package com.thejohnsondev.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
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
    }
}