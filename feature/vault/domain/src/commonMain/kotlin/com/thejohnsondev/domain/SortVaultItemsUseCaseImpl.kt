package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import com.thejohnsondev.ui.model.SortOrder

class SortVaultItemsUseCaseImpl : SortVaultItemsUseCase {
    override suspend operator fun invoke(
        unsortedList: List<PasswordUIModel>,
        sortOrder: SortOrder,
        showFavoritesAtTop: Boolean,
    ): List<PasswordUIModel> {
        val sortedList = when (sortOrder) {
            SortOrder.DATE_DESC -> unsortedList.sortedByDescending {
                it.modifiedTime ?: it.createdTime
            }

            SortOrder.DATE_ASC -> unsortedList.sortedBy { it.modifiedTime ?: it.createdTime }
            SortOrder.TITLE_DESC -> unsortedList.sortedByDescending { it.title }
            SortOrder.TITLE_ASC -> unsortedList.sortedBy { it.title }
        }

        return if (showFavoritesAtTop) {
            sortedList
                .sortedWith(compareByDescending<PasswordUIModel> {
                    it.isFavorite
                }.thenBy {
                    sortedList.indexOf(it)
                })
        } else {
            sortedList
        }
    }
}