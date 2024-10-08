package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.data.AuthRepository
import com.thejohnsondev.model.Error
import kotlinx.coroutines.flow.Flow

class ChangePasswordUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        oldPassword: String,
        newPassword: String
    ): Flow<Either<Error, Boolean>> {
        return authRepository.changePassword(oldPassword, newPassword)
    }

}