package com.thejohnsondev.database

import com.thejohnsondev.model.vault.PasswordDto
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {

    suspend fun getAllPasswords(): Flow<List<PasswordDto>>
    suspend fun createOrUpdatePassword(passwordDto: PasswordDto)
    suspend fun updatePasswords(passwordList: List<PasswordDto>)
    suspend fun deletePassword(passwordId: String)
    suspend fun updateIsFavorite(passwordId: String, isFavorite: Boolean)
    suspend fun getUnsynchronizedPasswords(): List<PasswordDto>
    suspend fun markAsSynchronised(passwordId: String, syncedTimeStamp: String)
    suspend fun getDeletedPasswordsIDs(): List<String>
    suspend fun deleteDeletedPasswordID(passwordId: String)

    suspend fun logout()
}