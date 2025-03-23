package com.thejohnsondev.domain

import com.thejohnsondev.data.PasswordsRepository
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.sync.SyncManager
import kotlinx.coroutines.flow.Flow

class PasswordsServiceImpl(
    private val passwordsRepository: PasswordsRepository,
    private val syncManager: SyncManager
) : PasswordsService {
    override suspend fun getUserPasswords(): Flow<List<PasswordDto>> {
        return passwordsRepository.getUserPasswords()
    }

    override suspend fun createOrUpdatePassword(passwordDto: PasswordDto) {
        passwordsRepository.createOrUpdatePassword(passwordDto)
        syncManager.syncLocalToRemote()
    }

    override suspend fun deletePassword(passwordId: String) {
        passwordsRepository.deletePassword(passwordId)
        syncManager.syncLocalToRemote()
    }

    override suspend fun updateIsFavorite(passwordId: String, isFavorite: Boolean) {
        passwordsRepository.updateIsFavorite(passwordId, isFavorite)
    }
}