package com.thejohnsondev.domain

import com.thejohnsondev.data.PasswordsRepository
import com.thejohnsondev.model.vault.PasswordDto
import kotlinx.coroutines.flow.Flow

class PasswordsServiceImpl(
    private val passwordsRepository: PasswordsRepository
) : PasswordsService {
    override suspend fun getUserPasswords(): Flow<List<PasswordDto>> {
        return passwordsRepository.getUserPasswords()
    }

    override suspend fun createOrUpdatePassword(passwordDto: PasswordDto) {
        passwordsRepository.createOrUpdatePassword(passwordDto)
    }

    override suspend fun deletePassword(passwordId: String) {
        passwordsRepository.deletePassword(passwordId)
    }
}