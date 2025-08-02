package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

class EnterAdditionalFieldValueUseCase {
    operator fun invoke(
        id: String,
        value: String,
        currentList: List<AdditionalFieldDto>
    ): List<AdditionalFieldDto> {
        return currentList.map {
            if (it.id == id) {
                it.copy(value = value)
            } else {
                it
            }
        }
    }
}