package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.Filter

interface ItemTypeFilterChangeUseCase {
    operator fun invoke(
        filter: Filter,
        isSelected: Boolean,
        filtersList: List<Filter>
    ): List<Filter>
}