package com.thejohnsondev.network.interceptors

import com.thejohnsondev.datastore.PreferencesDataStore
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header

class AuthTokenInterceptorImpl(
    private val preferenceDataStore: PreferencesDataStore,
) : AuthTokenInterceptor {

    private var token: String? = null

    companion object {
        private const val AUTH_HEADER = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "
    }

    private suspend fun getAuthToken(): String? {
        return token ?: run {
            token = preferenceDataStore.getAuthToken()
            token
        }
    }

    override suspend fun addAuthHeader(request: HttpRequestBuilder): HttpRequestBuilder {
        val token = getAuthToken()
        request.header(AUTH_HEADER, "$TOKEN_PREFIX$token")
        return request
    }
}
