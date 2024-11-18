package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.Filter

class ItemTypeFilterChangeUseCaseImpl : ItemTypeFilterChangeUseCase {
    override fun invoke(
        filter: Filter,
        isSelected: Boolean,
        filtersList: List<Filter>
    ): List<Filter> {
        val allFilter = filtersList.find { it.id == Filter.FILTER_ALL }
        val otherFilters = filtersList.filter { it.id != Filter.FILTER_ALL }

        return when {
            filter.id == Filter.FILTER_ALL && isSelected -> {
                filtersList.map { it.copy(isSelected = true) }
            }
            filter.id == Filter.FILTER_ALL -> {
                filtersList
            }
            otherFilters.count { it.isSelected } == 1 && !isSelected -> {
                filtersList
            }
            else -> {
                val updatedFilters = otherFilters.map {
                    if (it.id == filter.id) {
                        it.copy(isSelected = isSelected)
                    } else {
                        it
                    }
                }
                val areAllOtherFiltersSelected = updatedFilters.all { it.isSelected }
                val updatedAllFilter = allFilter?.copy(isSelected = areAllOtherFiltersSelected)
                val finalFilters = listOfNotNull(updatedAllFilter) + updatedFilters

                if (finalFilters.none { it.isSelected }) {
                    finalFilters.map { it.copy(isSelected = true) }
                } else {
                    finalFilters
                }
            }
        }
    }
}