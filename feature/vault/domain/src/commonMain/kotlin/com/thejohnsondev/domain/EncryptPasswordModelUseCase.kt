package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.EncryptionRepository
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto

class EncryptPasswordModelUseCase(
    private val encryptionRepository: EncryptionRepository
) {
    suspend operator fun invoke(passwordDto: PasswordDto): PasswordDto {
        return passwordDto.copy(
            title = encryptionRepository.encrypt(passwordDto.title),
            organizationLogo = passwordDto.organizationLogo?.let { encryptionRepository.encrypt(it) },
            domain = passwordDto.domain?.let { encryptionRepository.encrypt(it) },
            userName = encryptionRepository.encrypt(passwordDto.userName),
            password = encryptionRepository.encrypt(passwordDto.password),
            additionalFields = passwordDto.additionalFields.map { encryptAdditionalField(it) },
            createdTimeStamp = passwordDto.createdTimeStamp?.let { encryptionRepository.encrypt(it) },
            modifiedTimeStamp = passwordDto.modifiedTimeStamp?.let { encryptionRepository.encrypt(it) },
        )
    }

    private suspend fun encryptAdditionalField(additionalFieldDto: AdditionalFieldDto): AdditionalFieldDto {
        return additionalFieldDto.copy(
            title = encryptionRepository.encrypt(additionalFieldDto.title),
            value = encryptionRepository.encrypt(additionalFieldDto.value)
        )
    }
}