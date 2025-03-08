package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthDeleteAccountBody
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.model.auth.firebase.FBRefreshTokenRequestBody
import com.thejohnsondev.model.auth.firebase.FBRefreshTokenResponseBody
import com.thejohnsondev.model.vault.PasswordDto

interface RemoteApi {
    suspend fun signUp(body: FBAuthRequestBody): Either<Error, FBAuthSignUpResponse>
    suspend fun signIn(body: FBAuthRequestBody): Either<Error, FBAuthSignInResponse>
    suspend fun deleteAccount(body: FBAuthDeleteAccountBody): Either<Error, Unit>
    suspend fun refreshToken(body: FBRefreshTokenRequestBody): Either<Error, FBRefreshTokenResponseBody>
    suspend fun createPassword(passwordDto: PasswordDto): Either<Error, Unit>
    suspend fun getPasswords(): Either<Error, List<PasswordDto>>
    suspend fun updatePassword(passwordDto: PasswordDto): Either<Error, Unit>
    suspend fun deletePassword(passwordID: String): Either<Error, Unit>
}