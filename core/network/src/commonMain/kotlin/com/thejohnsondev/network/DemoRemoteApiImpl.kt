package com.thejohnsondev.network

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.HttpError
import com.thejohnsondev.model.auth.firebase.FBAuthDeleteAccountBody
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.model.auth.firebase.FBRefreshTokenRequestBody
import com.thejohnsondev.model.auth.firebase.FBRefreshTokenResponseBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DemoRemoteApiImpl: RemoteApi {
    private val accounts = mutableListOf<FBAuthRequestBody>()
    private val tokens = mutableMapOf<String, String>() // Stores email → token mapping

    override suspend fun signUp(body: FBAuthRequestBody): Either<Error, FBAuthSignUpResponse> {
        return withContext(Dispatchers.IO) {
            delay(500)

            if (accounts.any { it.email == body.email }) {
                Either.Left(HttpError(400,"Email already exists"))
            } else {
                accounts.add(body)
                val token = generateFakeToken()
                tokens[body.email] = token
                Either.Right(FBAuthSignUpResponse(idToken = token))
            }
        }
    }

    override suspend fun signIn(body: FBAuthRequestBody): Either<Error, FBAuthSignInResponse> {
        return withContext(Dispatchers.IO) {
            delay(500)

            val account = accounts.find { it.email == body.email && it.password == body.password }
            if (account != null) {
                val token = generateFakeToken()
                tokens[body.email] = token
                Either.Right(FBAuthSignInResponse(idToken = token))
            } else {
                Either.Left(HttpError(400,"Invalid credentials"))
            }
        }
    }

    override suspend fun deleteAccount(body: FBAuthDeleteAccountBody): Either<Error, Unit> {
        return withContext(Dispatchers.IO) {
            delay(500)

            if (accounts.isEmpty()) {
                Either.Right(Unit)
            } else {
                Either.Left(HttpError(404, "Account not found"))
            }
        }
    }

    override suspend fun refreshToken(body: FBRefreshTokenRequestBody): Either<Error, FBRefreshTokenResponseBody> {
        return withContext(Dispatchers.IO) {
            delay(500)

            val email = tokens.entries.find { it.value == body.refreshToken }?.key
            if (email != null) {
                val newToken = generateFakeToken()
                val refreshToken = generateFakeToken()
                tokens[email] = newToken
                Either.Right(FBRefreshTokenResponseBody(idToken = newToken, refreshToken = refreshToken))
            } else {
                Either.Left(HttpError(400,"Invalid refresh token"))
            }
        }
    }

    private fun generateFakeToken(): String = ""

}