package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.PasswordUIModel

interface CalculateListSizeUseCase {
    operator fun invoke(list: List<List<PasswordUIModel>>): Int
}