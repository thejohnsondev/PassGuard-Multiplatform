package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val preferencesDataStore: PreferencesDataStore
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        return flowOf(Either.Right(AuthResponse("token")))
    }

    override suspend fun singIn(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
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