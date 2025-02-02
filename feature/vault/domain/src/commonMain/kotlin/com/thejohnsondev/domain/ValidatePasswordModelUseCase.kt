package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

interface ValidatePasswordModelUseCase {
    suspend operator fun invoke(
        title: String,
        userName: String,
        password: String,
        additionalFieldsList: List<AdditionalFieldDto>
    ): Boolean
}