package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

interface ValidatePasswordModelUseCase {
    suspend operator fun invoke(
        organization: String,
        title: String,
        password: String,
        additionalFieldsList: List<AdditionalFieldDto>
    ): Boolean
}