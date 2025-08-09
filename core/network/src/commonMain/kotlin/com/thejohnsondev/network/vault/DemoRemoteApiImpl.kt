package com.thejohnsondev.network.vault

import arrow.core.Either
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_PERSONAL
import com.thejohnsondev.common.VAULT_ITEM_CATEGORY_WORK
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.HttpError
import com.thejohnsondev.model.auth.firebase.FBAuthDeleteAccountBody
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.model.auth.firebase.FBRefreshTokenRequestBody
import com.thejohnsondev.model.auth.firebase.FBRefreshTokenResponseBody
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.model.vault.SyncStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DemoRemoteApiImpl: RemoteApi {
    private val accounts = mutableListOf<FBAuthRequestBody>().apply {
        add(FBAuthRequestBody("test@test.com", "Pass123$", true))
    }
    private val tokens = mutableMapOf<String, String>() // Stores email → token mapping
    private val passwords = mutableListOf<PasswordDto>()

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

    override suspend fun createPassword(passwordDto: PasswordDto): Either<Error, Unit> {
        passwords.add(passwordDto)
        return Either.Right(Unit)
    }

    override suspend fun getPasswords(): Either<Error, List<PasswordDto>> {
        return Either.Right(passwords)
    }

    override suspend fun updatePassword(passwordDto: PasswordDto): Either<Error, Unit> {
        val index = passwords.indexOfFirst { it.id == passwordDto.id }
        if (index != -1) {
            passwords[index] = passwordDto
            return Either.Right(Unit)
        } else {
            return Either.Left(HttpError(404, "Password not found"))
        }
    }

    override suspend fun deletePassword(passwordID: String): Either<Error, Unit> {
        val index = passwords.indexOfFirst { it.id == passwordID }
        if (index != -1) {
            passwords.removeAt(index)
            return Either.Right(Unit)
        } else {
            return Either.Left(HttpError(404, "Password not found"))
        }
    }

    private fun generateFakeToken(): String = ""

}