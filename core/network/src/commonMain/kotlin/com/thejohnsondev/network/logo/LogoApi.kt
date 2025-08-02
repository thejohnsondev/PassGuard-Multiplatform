package com.thejohnsondev.network.logo

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.logo.FindLogoResponse

interface LogoApi {
    suspend fun find(query: String): Either<Error, List<FindLogoResponse>>
}