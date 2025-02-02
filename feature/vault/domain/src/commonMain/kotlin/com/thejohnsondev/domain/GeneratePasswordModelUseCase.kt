package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto

interface GeneratePasswordModelUseCase {
    operator fun invoke(
        passwordId: String?,
        title: String,
        userName: String,
        password: String,
        categoryId: String,
        additionalFields: List<AdditionalFieldDto>,
        createdTime: String?,
        isFavorite: Boolean
    ): PasswordDto
}