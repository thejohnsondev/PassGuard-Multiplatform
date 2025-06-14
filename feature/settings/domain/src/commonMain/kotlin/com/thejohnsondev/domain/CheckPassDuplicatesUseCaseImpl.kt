package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordDto

class CheckPassDuplicatesUseCaseImpl: CheckPassDuplicatesUseCase {
    override suspend operator fun invoke(
        savedPasswordsList: List<PasswordDto>,
        newPasswordsList: List<PasswordDto>
    ): CheckPassDuplicatesResult {
        fun PasswordDto.key() = Triple(title, userName, password)
        val savedKeys = savedPasswordsList.map { it.key() }.toSet()
        val duplicates = newPasswordsList.filter { it.key() in savedKeys }
        val duplicateKeys = duplicates.map { it.key() }.toSet()
        val listWithoutDuplicates = newPasswordsList.filter { it.key() !in duplicateKeys }
        return CheckPassDuplicatesResult(
            duplicates = duplicates,
            listWithoutDuplicates = listWithoutDuplicates
        )
    }
}