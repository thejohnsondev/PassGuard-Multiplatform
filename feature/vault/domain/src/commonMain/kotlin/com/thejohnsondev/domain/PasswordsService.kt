package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.models.PasswordUIModel
import kotlinx.coroutines.flow.Flow

interface PasswordsService {

    suspend fun getUserPasswords(): Flow<List<PasswordUIModel>>
    suspend fun createOrUpdatePassword(passwordUiModel: PasswordUIModel)
    suspend fun deletePassword(passwordId: String)

}