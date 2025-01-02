package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.datastore.PreferencesDataStore
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthDeleteAccountBody
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.network.RemoteApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AuthRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val remoteApi: RemoteApi
) : AuthRepository {

    override suspend fun signUp(
        body: FBAuthRequestBody,
        apiKey: String
    ): Flow<Either<Error, FBAuthSignUpResponse>> {
        return flowOf(remoteApi.signUp(body, apiKey))
    }

    override suspend fun singIn(
        body: FBAuthRequestBody,
        apiKey: String
    ): Flow<Either<Error, FBAuthSignInResponse>> {
        return flowOf(remoteApi.signIn(body, apiKey))
    }

    override suspend fun signOut() {
        localDataSource.logout()
        preferencesDataStore.clearUserData()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return preferencesDataStore.isUserLoggedIn()
    }

    override suspend fun deleteAccount(
        apiKey: String
    ): Flow<Either<Error, Unit>> {
        val token = preferencesDataStore.getAuthToken()
        val body = FBAuthDeleteAccountBody(token)
        return flowOf(remoteApi.deleteAccount(body, apiKey))
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