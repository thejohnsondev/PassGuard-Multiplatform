package com.thejohnsondev.data

import com.thejohnsondev.database.LocalDataSource
import com.thejohnsondev.domain.repo.PasswordsRepository
import com.thejohnsondev.model.vault.PasswordDto
import kotlinx.coroutines.flow.Flow

class PasswordsRepositoryImpl(
    private val localDataSource: LocalDataSource
): PasswordsRepository {
    override suspend fun getUserPasswords(): Flow<List<PasswordDto>> {
        return localDataSource.getAllPasswords()
    }

    override suspend fun createOrUpdatePassword(passwordDto: PasswordDto) {
        localDataSource.createOrUpdatePassword(passwordDto)
    }

    override suspend fun updatePasswords(passwordList: List<PasswordDto>) {
        localDataSource.updatePasswords(passwordList)
    }

    override suspend fun deletePassword(passwordId: String) {
        localDataSource.deletePassword(passwordId)
    }

    override suspend fun updateIsFavorite(passwordId: String, isFavorite: Boolean) {
        localDataSource.updateIsFavorite(passwordId, isFavorite)
    }
}