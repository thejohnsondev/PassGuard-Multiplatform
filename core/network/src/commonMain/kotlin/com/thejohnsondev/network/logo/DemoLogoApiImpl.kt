package com.thejohnsondev.network.logo

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.logo.FindLogoResponse

class DemoLogoApiImpl : LogoApi {
    override suspend fun find(query: String): Either<Error, List<FindLogoResponse>> {
        return Either.Right(listOf())
    }
}