package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel

interface SplitItemsListUseCase {
    operator fun invoke(isCompactScreen: Boolean, list: List<PasswordUIModel>): List<List<PasswordUIModel>>
}