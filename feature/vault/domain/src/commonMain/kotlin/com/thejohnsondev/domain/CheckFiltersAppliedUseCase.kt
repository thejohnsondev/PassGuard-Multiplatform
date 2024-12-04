package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.models.FilterUIModel

interface CheckFiltersAppliedUseCase {
    operator fun invoke(
        typeFiltersList: List<FilterUIModel>,
        categoryFiltersList: List<FilterUIModel>
    ): Boolean
}