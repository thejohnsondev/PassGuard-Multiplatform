package com.thejohnsondev.domain

import com.thejohnsondev.data.EncryptionRepository
import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto

class DecryptPasswordsListUseCaseImpl(
    private val encryptionRepository: EncryptionRepository
): DecryptPasswordsListUseCase {
    override suspend operator fun invoke(encryptedPasswords: List<PasswordDto>): List<PasswordDto> {
        return encryptedPasswords.map { passwordDto ->
            passwordDto.copy(
                title = encryptionRepository.decrypt(passwordDto.title),
                organizationLogo = passwordDto.organizationLogo?.let { encryptionRepository.decrypt(it) },
                userName = encryptionRepository.decrypt(passwordDto.userName),
                password = encryptionRepository.decrypt(passwordDto.password),
                additionalFields = passwordDto.additionalFields.map { decryptAdditionalField(it) },
                createdTimeStamp = passwordDto.createdTimeStamp?.let { encryptionRepository.decrypt(it) },
                modifiedTimeStamp = passwordDto.modifiedTimeStamp?.let { encryptionRepository.decrypt(it) },
            )
        }
    }

    private suspend fun decryptAdditionalField(additionalFieldDto: AdditionalFieldDto): AdditionalFieldDto {
        return additionalFieldDto.copy(
            title = encryptionRepository.decrypt(additionalFieldDto.title),
            value = encryptionRepository.decrypt(additionalFieldDto.value)
        )
    }
}