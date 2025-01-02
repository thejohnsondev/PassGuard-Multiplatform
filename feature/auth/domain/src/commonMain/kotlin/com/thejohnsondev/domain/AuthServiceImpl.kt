package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.data.AuthRepository
import com.thejohnsondev.data.EncryptionRepository
import com.thejohnsondev.data.GenerateKeyRepository
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import kotlinx.coroutines.flow.Flow
import org.thejohnsondev.domain.BuildKonfig

class AuthServiceImpl(
    private val authRepository: AuthRepository,
    private val generateKeyRepository: GenerateKeyRepository,
    private val encryptionRepository: EncryptionRepository
) : AuthService {

    private val apiKey: String by lazy {
        BuildKonfig.FIREBASE_API_KEY
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Either<Error, FBAuthSignInResponse>> {
        val requestBody = FBAuthRequestBody(email, password, true)
        return authRepository.singIn(requestBody, apiKey)
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, FBAuthSignUpResponse>> {
        val requestBody = FBAuthRequestBody(email, password, true)
        return authRepository.signUp(requestBody, apiKey)
    }

    override suspend fun logout() {
        authRepository.signOut()
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Flow<Either<Error, Boolean>> {
        return authRepository.changePassword(oldPassword, newPassword)
    }

    override suspend fun deleteAccount(): Flow<Either<Error, Unit>> {
        return authRepository.deleteAccount(apiKey)
    }

    override suspend fun generateKey(password: String): Flow<Either<Error, ByteArray>> {
        return generateKeyRepository.generateKey(password)
    }

    override suspend fun saveKey(key: ByteArray) {
        encryptionRepository.saveKey(key)
    }

    override suspend fun saveAuthToken(token: String) {
        authRepository.saveAuthToken(token)
    }

    override suspend fun saveEmail(email: String) {
        authRepository.saveEmail(email)
    }

}