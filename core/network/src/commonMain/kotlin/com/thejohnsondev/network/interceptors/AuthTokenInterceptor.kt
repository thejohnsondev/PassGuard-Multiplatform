package com.thejohnsondev.network.interceptors

import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.datastore.PreferencesDataStore
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header

class AuthTokenInterceptor(
    private val preferenceDataStore: PreferencesDataStore,
) {

    companion object {
        private const val TOKEN_PREFIX = "Bearer "
    }

    private suspend fun getAuthToken(): String {
        Logger.e("AuthTokenInterceptor", "getAuthToken")
        return preferenceDataStore.getAuthToken()
    }

    suspend fun addAuthHeader(request: HttpRequestBuilder): HttpRequestBuilder {
        val token = getAuthToken()
        request.header("Authorization", "$TOKEN_PREFIX$token")
        return request
    }
}
