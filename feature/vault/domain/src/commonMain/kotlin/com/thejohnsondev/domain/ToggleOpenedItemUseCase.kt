package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel

class ToggleOpenedItemUseCase {

    operator fun invoke(
        newOpenedItemId: String?,
        list: List<List<PasswordUIModel>>
    ): List<List<PasswordUIModel>> {
        val resultList = mutableListOf<List<PasswordUIModel>>()
        list.onEach {
            resultList.add(it.map { item ->
                item.copy(isExpanded = item.id == newOpenedItemId && !item.isExpanded)
            })
        }
        return resultList
    }
}