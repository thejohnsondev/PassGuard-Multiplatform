package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.common.encryption.EncryptionUtils
import com.thejohnsondev.model.Error
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GenerateKeyRepositoryImpl: GenerateKeyRepository {
    override fun generateKey(password: String): Flow<Either<Error, ByteArray>> {
        val generatedKey = EncryptionUtils.generateKey(password)
        return flowOf(Either.Right(generatedKey))
    }

}