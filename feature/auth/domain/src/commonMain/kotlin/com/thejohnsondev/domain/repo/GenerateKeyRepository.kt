package com.thejohnsondev.domain.repo

import arrow.core.Either
import com.thejohnsondev.model.Error
import kotlinx.coroutines.flow.Flow

interface GenerateKeyRepository {
    fun generateKeyWithPBKDF(password: String): Flow<Either<Error, ByteArray>>
    fun generateSecretKey(): ByteArray
}