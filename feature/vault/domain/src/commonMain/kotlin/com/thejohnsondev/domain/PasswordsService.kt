package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordDto
import kotlinx.coroutines.flow.Flow

interface PasswordsService {

    suspend fun getUserPasswords(): Flow<List<PasswordDto>>
    suspend fun createOrUpdatePassword(passwordDto: PasswordDto)
    suspend fun deletePassword(passwordId: String)

}