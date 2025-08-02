package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

class UpdateSelectedFiltersUseCase {

    operator fun invoke(
        filters: List<FilterUIModel>,
        appliedFiltersIDs: List<String>,
    ): List<FilterUIModel> {
        return filters.map {
            it.copy(isSelected = appliedFiltersIDs.contains(it.id))
        }
    }
}