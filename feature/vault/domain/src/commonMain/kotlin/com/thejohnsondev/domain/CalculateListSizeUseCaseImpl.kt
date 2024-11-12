package com.thejohnsondev.domain

import com.thejohnsondev.domain.models.PasswordUIModel

class CalculateListSizeUseCaseImpl : CalculateListSizeUseCase {

    companion object {
        private const val IDLE_ITEM_HEIGHT = 82
    }

    override fun invoke(list: List<List<PasswordUIModel>>): Int {
        if (list.size == 1) {
            return list.first().size * IDLE_ITEM_HEIGHT
        }
        val firstList = list.first()
        val secondList = list.last()
        if (firstList.size > secondList.size) {
            return firstList.size * IDLE_ITEM_HEIGHT
        } else if (firstList.size < secondList.size) {
            return secondList.size * IDLE_ITEM_HEIGHT
        } else if (firstList.size == secondList.size) {
            return firstList.size * IDLE_ITEM_HEIGHT
        }
        return 0
    }
}