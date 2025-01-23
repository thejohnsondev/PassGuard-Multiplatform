package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

class SortOrderChangeUseCaseImpl: SortOrderChangeUseCase {
    override fun invoke(
        filterUIModel: FilterUIModel,
        filtersList: List<FilterUIModel>,
    ): List<FilterUIModel> {
        return filtersList.map {
            it.copy(isSelected = it.id == filterUIModel.id)
        }
    }
}