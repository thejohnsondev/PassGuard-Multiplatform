package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.Filter

interface ItemTypeFilterChangeUseCase {
    operator fun invoke(
        filter: Filter,
        isSelected: Boolean,
        filtersList: List<Filter>
    ): List<Filter>
}