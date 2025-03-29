package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.model.vault.SyncStatus

interface GeneratePasswordModelUseCase {
    operator fun invoke(
        passwordId: String?,
        title: String,
        organizationLogoUrl: String,
        userName: String,
        password: String,
        categoryId: String,
        additionalFields: List<AdditionalFieldDto>,
        createdTime: String?,
        isFavorite: Boolean,
        syncStatus: SyncStatus
    ): PasswordDto
}