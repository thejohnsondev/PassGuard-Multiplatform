package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.models.FilterUIModel

interface ItemTypeFilterChangeUseCase {
    operator fun invoke(
        filterUIModel: FilterUIModel,
        isSelected: Boolean,
        filtersList: List<FilterUIModel>
    ): List<FilterUIModel>
}