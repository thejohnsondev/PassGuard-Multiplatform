package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel

class SplitItemsListUseCase {
    operator fun invoke(
        isCompactScreen: Boolean,
        list: List<PasswordUIModel>
    ): List<List<PasswordUIModel>> {
        if (list.isEmpty()) return emptyList()
        return if (isCompactScreen) {
            listOf(list)
        } else {
            val (firstList, secondList) = list.withIndex().partition { it.index % 2 == 0 }
            listOf(firstList.map { it.value }, secondList.map { it.value })
        }
    }
}