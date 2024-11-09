package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordUIModel

class ToggleOpenedItemUseCaseImpl : ToggleOpenedItemUseCase {

    override suspend fun invoke(
        newOpenedItemId: String?,
        list: List<PasswordUIModel>
    ): List<PasswordUIModel> {
        return list.map { item ->
            item.copy(isExpanded = item.id == newOpenedItemId && !item.isExpanded)
        }
    }
}