package com.thejohnsondev.data

import com.thejohnsondev.model.vault.PasswordDto
import kotlinx.coroutines.flow.Flow

interface PasswordsRepository {
    suspend fun getUserPasswords(): Flow<List<PasswordDto>>
    suspend fun createOrUpdatePassword(passwordDto: PasswordDto)
    suspend fun updatePasswords(passwordList: List<PasswordDto>)
    suspend fun deletePassword(passwordId: String)
}