package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.PasswordUIModel
import com.thejohnsondev.ui.model.SortOrder

interface SortVaultItemsUseCase {
    suspend operator fun invoke(
        unsortedList: List<PasswordUIModel>,
        sortOrder: SortOrder,
        showFavoritesAtTop: Boolean,
    ): List<PasswordUIModel>
}