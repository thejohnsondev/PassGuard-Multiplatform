package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordDto

interface DecryptPasswordsListUseCase {
    suspend operator fun invoke(encryptedPasswords: List<PasswordDto>): List<PasswordDto>
}