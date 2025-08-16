package com.thejohnsondev.domain.service

import com.thejohnsondev.model.vault.PasswordDto
import kotlinx.coroutines.flow.Flow

interface PasswordsService {

    suspend fun getUserPasswords(): Flow<List<PasswordDto>>
    suspend fun createOrUpdatePassword(passwordDto: PasswordDto)
    suspend fun deletePassword(passwordId: String)
    suspend fun updateIsFavorite(passwordId: String, isFavorite: Boolean)

}