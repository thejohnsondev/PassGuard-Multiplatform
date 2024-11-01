package com.thejohnsondev.network

import io.ktor.client.HttpClient

expect object HttpClientProvider {
    fun provide(): HttpClient
}