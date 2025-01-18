package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel
import com.thejohnsondev.ui.model.PasswordUIModel

interface FilterItemsUseCase {
    suspend operator fun invoke(
        items: List<PasswordUIModel>,
        typeFilters: List<FilterUIModel>,
        categoryFilters: List<FilterUIModel>
    ): List<PasswordUIModel>
}