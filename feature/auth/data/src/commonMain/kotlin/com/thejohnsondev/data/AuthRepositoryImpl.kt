package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryImpl: AuthRepository {
    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        return flowOf(Either.Right(AuthResponse("token")))
    }

    override suspend fun singIn(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        return flowOf(Either.Right(AuthResponse("token")))
    }

    override suspend fun signOut(): Flow<Either<Error, Unit>> {
        return flowOf(Either.Right(Unit))
    }

    override fun isUserLoggedIn(): Boolean {
        return false
    }

    override suspend fun deleteAccount(): Flow<Either<Error, Unit>> {
        return flowOf(Either.Right(Unit))
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Flow<Either<Error, Boolean>> {
        return flowOf(Either.Right(true))
    }

}