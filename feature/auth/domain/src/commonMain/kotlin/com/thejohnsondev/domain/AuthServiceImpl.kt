package com.thejohnsondev.domain

import arrow.core.Either
import com.thejohnsondev.data.AuthRepository
import com.thejohnsondev.data.GenerateKeyRepository
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthResponse
import kotlinx.coroutines.flow.Flow

class AuthServiceImpl(
    private val authRepository: AuthRepository,
    private val generateKeyRepository: GenerateKeyRepository
) : AuthService {

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        return authRepository.singIn(email, password)
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        return authRepository.signUp(email, password)
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
        authRepository.saveKey(key)
    }

    override suspend fun saveAuthToken(token: String) {
        authRepository.saveAuthToken(token)
    }

    override suspend fun saveEmail(email: String) {
        authRepository.saveEmail(email)
    }

}