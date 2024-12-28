package com.thejohnsondev.network.interceptors

import io.ktor.client.request.HttpRequestBuilder

interface AuthTokenInterceptor {
    suspend fun addAuthHeader(request: HttpRequestBuilder): HttpRequestBuilder
}