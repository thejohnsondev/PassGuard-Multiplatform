package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthDeleteAccountBody
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse

interface RemoteApi {
    suspend fun signUp(body: FBAuthRequestBody, apiKey: String): Either<Error, FBAuthSignUpResponse>
    suspend fun signIn(body: FBAuthRequestBody, apiKey: String): Either<Error, FBAuthSignInResponse>
    suspend fun deleteAccount(body: FBAuthDeleteAccountBody, apiKey: String): Either<Error, Unit>
}