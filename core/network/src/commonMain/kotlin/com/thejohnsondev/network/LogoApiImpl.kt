package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.logo.FindLogoResponse
import io.ktor.client.HttpClient

class LogoApiImpl(
    private val client: HttpClient

) : LogoApi {

    override suspend fun find(query: String): Either<Error, List<FindLogoResponse>> {
        TODO("Not yet implemented")
    }

}