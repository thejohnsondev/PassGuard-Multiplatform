package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.FilterUIModel

interface GetSelectedFiltersIDsUseCase {
    suspend operator fun invoke(filters: List<FilterUIModel>): List<String>
}