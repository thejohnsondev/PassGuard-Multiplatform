package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.AdditionalFieldDto

interface EnterAdditionalFieldTitleUseCase {
    operator fun invoke(id: String, title: String, currentList: List<AdditionalFieldDto>): List<AdditionalFieldDto>
}