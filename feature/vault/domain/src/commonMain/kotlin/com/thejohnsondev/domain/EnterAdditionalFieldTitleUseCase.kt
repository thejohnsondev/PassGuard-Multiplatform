package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

class EnterAdditionalFieldTitleUseCase {
    operator fun invoke(
        id: String,
        title: String,
        currentList: List<AdditionalFieldDto>
    ): List<AdditionalFieldDto> {
        return currentList.map {
            if (it.id == id) {
                it.copy(title = title)
            } else {
                it
            }
        }
    }
}