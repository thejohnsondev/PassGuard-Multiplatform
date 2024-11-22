package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.PasswordUIModel

interface CalculateListSizeUseCase {
    operator fun invoke(list: List<List<PasswordUIModel>>): Int
}