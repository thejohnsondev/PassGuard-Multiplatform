package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.PasswordUIModel

class SplitItemsListUseCaseImpl : SplitItemsListUseCase {
    override fun invoke(
        isCompactScreen: Boolean,
        list: List<PasswordUIModel>
    ): List<List<PasswordUIModel>> {
        return if (isCompactScreen) {
            listOf(list)
        } else {
            val (firstList, secondList) = list.withIndex().partition { it.index % 2 == 0 }
            listOf(firstList.map { it.value }, secondList.map { it.value })
        }
    }
}