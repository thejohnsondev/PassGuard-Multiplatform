package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(email: String, password: String): Flow<Either<Error, AuthResponse>>
    suspend fun singIn(email: String, password: String): Flow<Either<Error, AuthResponse>>
    suspend fun signOut()
    suspend fun isUserLoggedIn(): Boolean
    suspend fun deleteAccount(): Flow<Either<Error, Unit>>
    suspend fun changePassword(oldPassword: String, newPassword: String): Flow<Either<Error, Boolean>>
    suspend fun saveAuthToken(token: String)
    suspend fun saveEmail(email: String)

}