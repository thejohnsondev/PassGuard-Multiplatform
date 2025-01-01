package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

class CheckFiltersAppliedUseCaseImpl : CheckFiltersAppliedUseCase {
    override operator fun invoke(
        typeFiltersList: List<FilterUIModel>,
        categoryFiltersList: List<FilterUIModel>
    ): Boolean {
        return typeFiltersList.any { it.isSelected } || categoryFiltersList.any { it.isSelected }
    }
}