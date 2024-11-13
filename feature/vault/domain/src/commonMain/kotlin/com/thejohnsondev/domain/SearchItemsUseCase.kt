package com.thejohnsondev.domain

import com.thejohnsondev.domain.models.PasswordUIModel

interface SearchItemsUseCase {
    operator fun invoke(query: String, isDeepSearchEnabled: Boolean, list: List<PasswordUIModel>): List<PasswordUIModel>
}