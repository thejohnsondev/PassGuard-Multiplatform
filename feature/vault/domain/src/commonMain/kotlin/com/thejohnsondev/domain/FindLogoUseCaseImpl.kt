package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.data.VaultRepository
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.logo.FindLogoResponse

class FindLogoUseCaseImpl(
    private val vaultRepository: VaultRepository
): FindLogoUseCase {
    override suspend fun invoke(query: String): Either<Error, List<FindLogoResponse>> {
        return vaultRepository.findLogo(query)
    }
}