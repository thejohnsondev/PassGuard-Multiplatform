package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

interface RemoveAdditionalFieldUseCase {
    operator fun invoke(id: String, currentList: List<AdditionalFieldDto>): List<AdditionalFieldDto>
}