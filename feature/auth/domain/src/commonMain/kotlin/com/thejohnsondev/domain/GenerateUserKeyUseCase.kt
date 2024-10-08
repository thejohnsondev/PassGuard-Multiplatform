package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.data.GenerateKeyRepository
import com.thejohnsondev.model.Error
import kotlinx.coroutines.flow.Flow

class GenerateUserKeyUseCase(
    private val generateKeyRepository: GenerateKeyRepository
) {
    operator fun invoke(password: String): Flow<Either<Error, ByteArray>> {
        return generateKeyRepository.generateKey(password)
    }
}