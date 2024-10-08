package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.data.AuthRepository
import com.thejohnsondev.model.Error
import kotlinx.coroutines.flow.Flow

class DeleteAccountUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Either<Error, Unit>> {
        return authRepository.deleteAccount()
    }
}