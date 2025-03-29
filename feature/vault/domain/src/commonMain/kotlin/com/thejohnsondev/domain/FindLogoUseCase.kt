package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.logo.FindLogoResponse

interface FindLogoUseCase {
    suspend operator fun invoke(query: String): Either<Error, List<FindLogoResponse>>
}