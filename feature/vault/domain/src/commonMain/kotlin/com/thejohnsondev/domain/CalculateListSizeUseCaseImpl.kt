package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.PasswordUIModel

class CalculateListSizeUseCaseImpl : CalculateListSizeUseCase {

    companion object {
        const val PASSWORD_IDLE_ITEM_HEIGHT = 82
        const val PASSWORD_EXPANDED_ITEM_HEIGHT = 184
        const val ADDITIONAL_FIELD_HEIGHT = 84
    }

    override fun invoke(list: List<List<PasswordUIModel>>): Int {
        if (list.size == 1) {
            val firstList = list.first()
            val expandedItem = firstList.firstOrNull { it.isExpanded }
            val additionalFieldsCount = expandedItem?.additionalFields?.size ?: 0
            return (firstList.size * PASSWORD_IDLE_ITEM_HEIGHT) + (expandedItem?.let { PASSWORD_EXPANDED_ITEM_HEIGHT + (additionalFieldsCount * ADDITIONAL_FIELD_HEIGHT) } ?: 0)
        }
        val firstList = list.first()
        val secondList = list.last()
        var firstListSize = firstList.size * PASSWORD_IDLE_ITEM_HEIGHT
        var secondListSize = secondList.size * PASSWORD_IDLE_ITEM_HEIGHT

        firstList.firstOrNull { it.isExpanded }?.let { expandedItem ->
            val additionalFieldsCount = expandedItem.additionalFields.size
            firstListSize += PASSWORD_EXPANDED_ITEM_HEIGHT + (additionalFieldsCount * ADDITIONAL_FIELD_HEIGHT)
        }

        secondList.firstOrNull { it.isExpanded }?.let { expandedItem ->
            val additionalFieldsCount = expandedItem.additionalFields.size
            secondListSize += PASSWORD_EXPANDED_ITEM_HEIGHT + (additionalFieldsCount * ADDITIONAL_FIELD_HEIGHT)
        }

        return maxOf(firstListSize, secondListSize)
    }
}