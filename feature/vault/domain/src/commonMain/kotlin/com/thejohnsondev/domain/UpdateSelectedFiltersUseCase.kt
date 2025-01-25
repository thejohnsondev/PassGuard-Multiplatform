package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

interface UpdateSelectedFiltersUseCase {
    suspend operator fun invoke(
        filters: List<FilterUIModel>,
        appliedFiltersIDs: List<String>
    ): List<FilterUIModel>
}