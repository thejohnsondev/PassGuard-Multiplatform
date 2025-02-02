package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.model.auth.firebase.FBRefreshTokenResponseBody
import kotlinx.coroutines.flow.Flow

interface AuthService {

    suspend fun signIn(
        email: String,
        password: String
    ): Flow<Either<Error, FBAuthSignInResponse>>

    suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, FBAuthSignUpResponse>>

    suspend fun logout()

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Flow<Either<Error, Boolean>>

    suspend fun deleteAccount(): Flow<Either<Error, Unit>>

    suspend fun generateSecretKey()

    suspend fun saveAuthToken(authToken: String)
    suspend fun saveRefreshAuthToken(refreshAuthToken: String)

    suspend fun saveEmail(email: String)
    suspend fun refreshToken(): Flow<Either<Error, FBRefreshTokenResponseBody>>

}