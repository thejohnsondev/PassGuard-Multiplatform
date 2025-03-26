package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.logo.FindLogoResponse
import com.thejohnsondev.model.auth.logo.LogoApiKey
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class LogoApiImpl(
    private val client: HttpClient,
    private val logoApiKey: LogoApiKey
) : LogoApi {

    override suspend fun find(query: String): Either<Error, List<FindLogoResponse>> {
        return callWithMapping {
            client.get(urlString = "$LOGO_API_BASE_URL/search?q=$query") {
                headers.append("Authorization", "Bearer: ${logoApiKey.key}")
            }
        }
    }
}