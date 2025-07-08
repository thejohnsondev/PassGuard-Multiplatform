package com.thejohnsondev.data

import arrow.core.Either
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.auth.firebase.FBAuthRequestBody
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
import com.thejohnsondev.model.auth.firebase.FBAuthSignUpResponse
import com.thejohnsondev.model.auth.firebase.FBRefreshTokenResponseBody
import com.thejosnsondev.biometric.BiometricAuthResult
import com.thejosnsondev.biometric.BiometricAvailability
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(
        body: FBAuthRequestBody
    ): Flow<Either<Error, FBAuthSignUpResponse>>

    suspend fun singIn(
        body: FBAuthRequestBody
    ): Flow<Either<Error, FBAuthSignInResponse>>
    suspend fun signOut()
    suspend fun isVaultInitialized(): Boolean
    suspend fun isVaultLocal(): Boolean
    suspend fun deleteAccount(): Flow<Either<Error, Unit>>
    suspend fun changePassword(oldPassword: String, newPassword: String): Flow<Either<Error, Boolean>>
    suspend fun saveAuthToken(token: String)
    suspend fun saveEmail(email: String)
    suspend fun saveRefreshAuthToken(refreshAuthToken: String)
    suspend fun refreshToken(): Flow<Either<Error, FBRefreshTokenResponseBody>>
    suspend fun isUseBiometrics(): Boolean
    suspend fun showBiometricPrompt(
        promptTitle: String,
        promptSubtitle: String?,
        promptDescription: String?
    ): BiometricAuthResult
    suspend fun getBiometricAvailability(): BiometricAvailability

}