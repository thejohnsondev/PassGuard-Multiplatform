package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.PasswordUIModel

interface SortVaultItemsUseCase {
    suspend operator fun invoke(
        unsortedList: List<PasswordUIModel>,
        sortOrder: SortOrder,
        keepFavoriteAtTop: Boolean,
    ): List<PasswordUIModel>
}

enum class SortOrder {
    DATE_DESC,
    DATE_ASC,
    ALPHABETICAL_DESC,
    ALPHABETICAL_ASC,
}