package com.thejohnsondev.domain

import com.thejohnsondev.uimodel.models.PasswordUIModel

interface SplitItemsListUseCase {
    operator fun invoke(isCompactScreen: Boolean, list: List<PasswordUIModel>): List<List<PasswordUIModel>>
}