package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

interface EnterAdditionalFieldValueUseCase {
    operator fun invoke(id: String, value: String, currentList: List<AdditionalFieldDto>): List<AdditionalFieldDto>
}