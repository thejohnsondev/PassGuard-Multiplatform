package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

class SortOrderChangeUseCase {
    operator fun invoke(
        filterUIModel: FilterUIModel,
        filtersList: List<FilterUIModel>,
    ): List<FilterUIModel> {
        return filtersList.map {
            it.copy(isSelected = it.id == filterUIModel.id)
        }
    }
}