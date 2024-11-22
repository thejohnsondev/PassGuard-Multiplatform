package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.FilterUIModel

class ItemTypeFilterChangeUseCaseImpl : ItemTypeFilterChangeUseCase {
    override fun invoke(
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