package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthRequestBody
import com.thejohnsondev.model.auth.AuthResponse

interface RemoteApi {
    suspend fun signUp(body: AuthRequestBody): Either<Error, AuthResponse>
    suspend fun signIn(body: AuthRequestBody): Either<Error, AuthResponse>
    suspend fun deleteAccount(): Either<Error, Unit>
}