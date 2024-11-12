package com.thejohnsondev.domain

import com.thejohnsondev.domain.models.PasswordUIModel

interface CalculateListSizeUseCase {
    operator fun invoke(list: List<List<PasswordUIModel>>): Int
}