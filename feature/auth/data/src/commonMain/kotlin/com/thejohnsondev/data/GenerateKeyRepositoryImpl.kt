package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.platform.KeyGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GenerateKeyRepositoryImpl(
    private val keyGenerator: KeyGenerator
): GenerateKeyRepository {
    override fun generateKeyWithPBKDF(password: String): Flow<Either<Error, ByteArray>> {
        val generatedKey = keyGenerator.generateKeyWithPBKDF(password)
        return flowOf(Either.Right(generatedKey))
    }

    override fun generateSecretKey(): ByteArray {
        val generatedKey = keyGenerator.generateSecretKey()
        return generatedKey
    }

}