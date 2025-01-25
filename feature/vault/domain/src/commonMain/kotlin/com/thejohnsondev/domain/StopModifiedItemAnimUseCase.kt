package com.thejohnsondev.domain

import com.thejohnsondev.ui.model.PasswordUIModel

interface StopModifiedItemAnimUseCase {
    suspend operator fun invoke(
        passwordsList: List<List<PasswordUIModel>>,
    ): List<PasswordUIModel>
}