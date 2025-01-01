package com.thejohnsondev.domain

import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.ui.model.PasswordUIModel

interface PasswordsMapToUiModelsUseCase {
    operator fun invoke(passwordsDto: List<PasswordDto>): List<PasswordUIModel>
}