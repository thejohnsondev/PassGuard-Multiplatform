package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.common.encryption.EncryptionUtils
import com.thejohnsondev.model.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GenerateKeyRepositoryImpl: GenerateKeyRepository {
    override fun generateKeyWithPBKDF(password: String): Flow<Either<Error, ByteArray>> {
        val generatedKey = EncryptionUtils.generateKeyWithPBKDF(password)
        return flowOf(Either.Right(generatedKey))
    }

    override fun generateSecretKey(): ByteArray {
        val generatedKey = EncryptionUtils.generateSecretKey()
        return generatedKey
    }

}