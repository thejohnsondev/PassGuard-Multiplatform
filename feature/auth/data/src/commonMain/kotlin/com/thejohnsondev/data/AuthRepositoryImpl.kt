package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.common.encryption.EncryptionUtils
import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthResponse
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.thejohnsondev.data.BuildKonfig
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class AuthRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val preferencesDataStore: PreferencesDataStore
) : AuthRepository {

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        val encryptedEmail = EncryptionUtils.encrypt(
            email,
            Base64.decode(BuildKonfig.AUTH_SECRET_KEY.toByteArray()),
            Base64.decode(BuildKonfig.AUTH_SECRET_IV.toByteArray())
        )
        val encryptedPassword = EncryptionUtils.encrypt(
            password,
            Base64.decode(BuildKonfig.AUTH_SECRET_KEY.toByteArray()),
            Base64.decode(BuildKonfig.AUTH_SECRET_IV.toByteArray())
        )
        return flowOf(Either.Right(AuthResponse("token")))
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun singIn(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        val encryptedEmail = EncryptionUtils.encrypt(
            email,
            Base64.decode(BuildKonfig.AUTH_SECRET_KEY.toByteArray()),
            Base64.decode(BuildKonfig.AUTH_SECRET_IV.toByteArray())
        )
        val encryptedPassword = EncryptionUtils.encrypt(
            password,
            Base64.decode(BuildKonfig.AUTH_SECRET_KEY.toByteArray()),
            Base64.decode(BuildKonfig.AUTH_SECRET_IV.toByteArray())
        )
        return flowOf(Either.Right(AuthResponse("token")))
    }

    override suspend fun signOut() {
        localDataSource.logout()
        preferencesDataStore.clearUserData()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return preferencesDataStore.isUserLoggedIn()
    }

    override suspend fun deleteAccount(): Flow<Either<Error, Unit>> {
        return flowOf(Either.Right(Unit))
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Flow<Either<Error, Boolean>> {
        return flowOf(Either.Right(true))
    }

    override suspend fun saveKey(key: ByteArray) {
        preferencesDataStore.saveKey(key)
    }

    override suspend fun saveAuthToken(token: String) {
        preferencesDataStore.saveAuthToken(token)
    }

    override suspend fun saveEmail(email: String) {
        preferencesDataStore.saveEmail(email)
    }

}