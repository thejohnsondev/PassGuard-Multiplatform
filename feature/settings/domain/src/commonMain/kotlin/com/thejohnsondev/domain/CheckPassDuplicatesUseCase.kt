package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordDto

interface CheckPassDuplicatesUseCase {
    suspend operator fun invoke(
        savedPasswordsList: List<PasswordDto>,
        newPasswordsList: List<PasswordDto>
    ): CheckPassDuplicatesResult
}

data class CheckPassDuplicatesResult(
    val duplicates: List<PasswordDto>,
    val listWithoutDuplicates: List<PasswordDto>
)