package com.thejohnsondev.domain

import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel

interface StopModifiedItemAnimUseCase {
    suspend operator fun invoke(
        passwordsList: List<PasswordUIModel>,
    ): List<PasswordUIModel>
}