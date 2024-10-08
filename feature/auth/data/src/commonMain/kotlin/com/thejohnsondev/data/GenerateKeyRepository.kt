package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.model.Error
import kotlinx.coroutines.flow.Flow

interface GenerateKeyRepository {
    fun generateKey(password: String): Flow<Either<Error, ByteArray>>
}