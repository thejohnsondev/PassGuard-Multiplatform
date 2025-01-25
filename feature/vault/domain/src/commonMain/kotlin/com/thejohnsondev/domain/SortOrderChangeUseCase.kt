package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

interface SortOrderChangeUseCase {
    operator fun invoke(
        filterUIModel: FilterUIModel,
        filtersList: List<FilterUIModel>
    ): List<FilterUIModel>
}