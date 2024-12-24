package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

interface CheckFiltersAppliedUseCase {
    operator fun invoke(
        typeFiltersList: List<FilterUIModel>,
        categoryFiltersList: List<FilterUIModel>
    ): Boolean
}