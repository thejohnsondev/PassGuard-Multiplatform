package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.PasswordUIModel

interface ToggleOpenedItemUseCase {
    suspend operator fun invoke(newOpenedItemId: String?, list: List<List<PasswordUIModel>>): List<List<PasswordUIModel>>
}