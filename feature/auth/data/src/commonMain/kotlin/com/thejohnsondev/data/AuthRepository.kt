package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(
        body: FBAuthRequestBody,
        apiKey: String
    ): Flow<Either<Error, FBAuthSignUpResponse>>

    suspend fun singIn(
        body: FBAuthRequestBody,
        apiKey: String
    ): Flow<Either<Error, FBAuthSignInResponse>>
    suspend fun signOut()
    suspend fun isUserLoggedIn(): Boolean
    suspend fun deleteAccount(
        apiKey: String
    ): Flow<Either<Error, Unit>>
    suspend fun changePassword(oldPassword: String, newPassword: String): Flow<Either<Error, Boolean>>
    suspend fun saveAuthToken(token: String)
    suspend fun saveEmail(email: String)

}