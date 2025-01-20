package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.PasswordUIModel

class SortVaultItemsUseCaseImpl : SortVaultItemsUseCase {
    override suspend operator fun invoke(
        unsortedList: List<PasswordUIModel>,
        sortOrder: SortOrder,
        keepFavoriteAtTop: Boolean,
    ): List<PasswordUIModel> {
        val sortedList = when (sortOrder) {
            SortOrder.DATE_DESC -> unsortedList.sortedByDescending {
                it.modifiedTime ?: it.createdTime
            }

            SortOrder.DATE_ASC -> unsortedList.sortedBy { it.modifiedTime ?: it.createdTime }
            SortOrder.ALPHABETICAL_DESC -> unsortedList.sortedByDescending { it.organization }
            SortOrder.ALPHABETICAL_ASC -> unsortedList.sortedBy { it.organization }
        }

        return if (keepFavoriteAtTop) {
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