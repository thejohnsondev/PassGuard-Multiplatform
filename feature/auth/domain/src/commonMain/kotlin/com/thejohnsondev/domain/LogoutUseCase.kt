package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.data.AuthRepository
import com.thejohnsondev.model.Error
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun invoke(): Flow<Either<Error, Unit>> {
        return authRepository.signOut()
    }
}