package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordDto

interface EncryptPasswordModelUseCase {
    suspend operator fun invoke(passwordDto: PasswordDto): PasswordDto
}