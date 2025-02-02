package com.thejohnsondev.database

import app.cash.sqldelight.coroutines.asFlow
import com.thejohnsondev.database.mappers.mapToDto
import com.thejohnsondev.model.vault.PasswordDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.thejohnsondev.vault.database.VaultDatabase

class LocalDataSourceImpl(
    private val vaultDatabase: VaultDatabase
) : LocalDataSource {
    override suspend fun getUserPasswords(): Flow<List<PasswordDto>> {
        return vaultDatabase.passwordEntityQueries.getAll().asFlow().map {
            it.executeAsList().map { passwordEntity ->
                passwordEntity.mapToDto(
                    vaultDatabase.additionalFieldEntityQueries.getByPasswordId(passwordEntity.id)
                        .executeAsList().map { additionalFieldEntity ->
                            additionalFieldEntity.mapToDto()
                        }
                )
            }
        }
    }

    override suspend fun createOrUpdatePassword(passwordDto: PasswordDto) {
        vaultDatabase.passwordEntityQueries.insert(
            title = passwordDto.title,
            organizationLogo = passwordDto.organizationLogo,
            userName = passwordDto.userName,
            password = passwordDto.password,
            createdTimeStamp = passwordDto.createdTimeStamp,
            modifiedTimeStamp = passwordDto.modifiedTimeStamp,
            categoryId = passwordDto.categoryId,
            isFavorite = passwordDto.isFavorite,
            id = passwordDto.id
        )
        vaultDatabase.additionalFieldEntityQueries.deleteByPasswordId(passwordDto.id)
        passwordDto.additionalFields.forEach {
            vaultDatabase.additionalFieldEntityQueries.insert(
                passwordId = passwordDto.id,
                fieldTitle = it.title,
                fieldValue = it.value,
                id = it.id
            )
        }
    }

    override suspend fun updatePasswords(passwordList: List<PasswordDto>) {
        passwordList.forEach {
            createOrUpdatePassword(it)
        }
    }

    override suspend fun deletePassword(passwordId: String) {
        vaultDatabase.passwordEntityQueries.deleteById(passwordId)
        vaultDatabase.additionalFieldEntityQueries.deleteByPasswordId(passwordId)
    }

    override suspend fun updateIsFavorite(passwordId: String, isFavorite: Boolean) {
        vaultDatabase.passwordEntityQueries.updateIsFavorite(isFavorite = isFavorite, id = passwordId)
    }

    override suspend fun logout() {
        vaultDatabase.passwordEntityQueries.deleteAll()
        vaultDatabase.additionalFieldEntityQueries.deleteAll()
    }
}