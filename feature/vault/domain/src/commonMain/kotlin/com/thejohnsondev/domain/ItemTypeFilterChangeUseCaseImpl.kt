package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.Filter

class ItemTypeFilterChangeUseCaseImpl : ItemTypeFilterChangeUseCase {
    override fun invoke(
        filter: Filter,
        isSelected: Boolean,
        filtersList: List<Filter>
    ): List<Filter> {
        return filtersList.map {
            if (it.id == filter.id) {
                it.copy(isSelected = isSelected)
            } else {
                it
            }
        }
    }
}