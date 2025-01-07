package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
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

    suspend fun generateKey(password: String): Flow<Either<Error, ByteArray>>

    suspend fun saveKey(key: ByteArray)

    suspend fun saveAuthToken(token: String)

    suspend fun saveEmail(email: String)

}