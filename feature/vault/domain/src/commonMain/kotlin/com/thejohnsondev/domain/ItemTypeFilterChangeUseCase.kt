package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

interface ItemTypeFilterChangeUseCase {
    operator fun invoke(
        filterUIModel: FilterUIModel,
        isSelected: Boolean,
        filtersList: List<FilterUIModel>
    ): List<FilterUIModel>
}