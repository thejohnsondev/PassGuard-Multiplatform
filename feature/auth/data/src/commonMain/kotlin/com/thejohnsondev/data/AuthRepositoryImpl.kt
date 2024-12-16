package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.AuthRequestBody
import com.thejohnsondev.model.auth.AuthResponse
import com.thejohnsondev.network.RemoteApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.io.encoding.ExperimentalEncodingApi

class AuthRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val remoteApi: RemoteApi
) : AuthRepository {

    override suspend fun signUp(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>>  {
        val requestBody = AuthRequestBody(email, password)
        return flowOf(remoteApi.signUp(requestBody))
    }

    override suspend fun singIn(
        email: String,
        password: String
    ): Flow<Either<Error, AuthResponse>> {
        val requestBody = AuthRequestBody(email, password)
        return flowOf(remoteApi.signIn(requestBody))
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

    override suspend fun saveAuthToken(token: String) {
        preferencesDataStore.saveAuthToken(token)
    }

    override suspend fun saveEmail(email: String) {
        preferencesDataStore.saveEmail(email)
    }

}