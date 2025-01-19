package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

class GetSelectedFiltersIDsUseCaseImpl: GetSelectedFiltersIDsUseCase {
    override suspend fun invoke(filters: List<FilterUIModel>): List<String> {
        return filters.filter { it.isSelected }.map { it.id }
    }
}