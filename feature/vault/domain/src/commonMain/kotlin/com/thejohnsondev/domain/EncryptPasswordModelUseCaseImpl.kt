package com.thejohnsondev.domain

import com.thejohnsondev.data.EncryptionRepository
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto

class EncryptPasswordModelUseCaseImpl(
    private val encryptionRepository: EncryptionRepository
) : EncryptPasswordModelUseCase {
    override suspend operator fun invoke(passwordDto: PasswordDto): PasswordDto {
        return passwordDto.copy(
            organization = encryptionRepository.encrypt(passwordDto.organization),
            organizationLogo = passwordDto.organizationLogo?.let { encryptionRepository.encrypt(it) },
            title = encryptionRepository.encrypt(passwordDto.title),
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