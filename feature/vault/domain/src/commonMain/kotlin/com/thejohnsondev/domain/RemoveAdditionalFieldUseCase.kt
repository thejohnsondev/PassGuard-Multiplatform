package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

class RemoveAdditionalFieldUseCase {
    operator fun invoke(
        id: String,
        currentList: List<AdditionalFieldDto>
    ): List<AdditionalFieldDto> {
        return currentList.filter { it.id != id }
    }
}