package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

interface ItemFilterChangeUseCase {
    operator fun invoke(
        filterUIModel: FilterUIModel,
        isSelected: Boolean,
        filtersList: List<FilterUIModel>
    ): List<FilterUIModel>
}