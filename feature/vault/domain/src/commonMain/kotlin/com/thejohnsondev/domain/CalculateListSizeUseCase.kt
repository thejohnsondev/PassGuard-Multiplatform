package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel

interface CalculateListSizeUseCase {
    operator fun invoke(list: List<List<PasswordUIModel>>): Int
}