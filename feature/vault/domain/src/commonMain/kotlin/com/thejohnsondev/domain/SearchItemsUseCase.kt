package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel

interface SearchItemsUseCase {
    operator fun invoke(query: String, isDeepSearchEnabled: Boolean, list: List<PasswordUIModel>): List<PasswordUIModel>
}