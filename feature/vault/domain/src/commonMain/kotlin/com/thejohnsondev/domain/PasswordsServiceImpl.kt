package com.thejohnsondev.domain

import com.thejohnsondev.data.PasswordsRepository
import com.thejohnsondev.uimodel.mappers.mapToDto

import com.thejohnsondev.uimodel.mappers.mapToUiModel
import com.thejohnsondev.uimodel.models.PasswordUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PasswordsServiceImpl(
    private val passwordsRepository: PasswordsRepository
) : PasswordsService {
    override suspend fun getUserPasswords(): Flow<List<PasswordUIModel>> {
        return passwordsRepository.getUserPasswords().map { passwordsList ->
            passwordsList.map { passwordDto ->
                passwordDto.mapToUiModel()
            }
        }
    }

    override suspend fun createOrUpdatePassword(passwordUiModel: PasswordUIModel) {
        passwordsRepository.createOrUpdatePassword(passwordUiModel.mapToDto())
    }

    override suspend fun deletePassword(passwordId: String) {
        passwordsRepository.deletePassword(passwordId)
    }
}