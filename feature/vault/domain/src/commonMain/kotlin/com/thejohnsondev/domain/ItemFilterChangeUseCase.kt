package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

class ItemFilterChangeUseCase {
    operator fun invoke(
        filterUIModel: FilterUIModel,
        isSelected: Boolean,
        filtersList: List<FilterUIModel>
    ): List<FilterUIModel> {
        return filtersList.map {
            if (it.id == filterUIModel.id) {
                it.copy(isSelected = isSelected)
            } else {
                it
            }
        }
    }
}