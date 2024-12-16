package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.data.AuthRepository
import com.thejohnsondev.data.EncryptionRepository
import com.thejohnsondev.data.GenerateKeyRepository
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthResponse
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.flow.Flow
import org.thejohnsondev.domain.BuildKonfig
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AuthServiceImpl(
    private val authRepository: AuthRepository,
    private val generateKeyRepository: GenerateKeyRepository,
    private val encryptionRepository: EncryptionRepository
) : AuthService {

    @OptIn(ExperimentalEncodingApi::class)
    private val authSecretKey: ByteArray by lazy {
        Base64.decode(BuildKonfig.AUTH_SECRET_KEY.toByteArray())
    }

    @OptIn(ExperimentalEncodingApi::class)
    private val authSecretIv: ByteArray by lazy {
        Base64.decode(BuildKonfig.AUTH_SECRET_IV.toByteArray())
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        val encryptedEmail = encryptionRepository.encrypt(
            email,
            authSecretKey,
            authSecretIv
        )
        val encryptedPassword = encryptionRepository.encrypt(
            password,
            authSecretKey,
            authSecretIv
        )
        return authRepository.singIn(encryptedEmail, encryptedPassword)
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        val encryptedEmail = encryptionRepository.encrypt(
            email,
            authSecretKey,
            authSecretIv
        )
        val encryptedPassword = encryptionRepository.encrypt(
            password,
            authSecretKey,
            authSecretIv
        )
        return authRepository.signUp(encryptedEmail, encryptedPassword)
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
        return authRepository.deleteAccount()
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