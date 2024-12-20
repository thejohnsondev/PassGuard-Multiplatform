package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

interface AddAdditionalFieldUseCase {
    operator fun invoke(currentList: List<AdditionalFieldDto>): List<AdditionalFieldDto>
}