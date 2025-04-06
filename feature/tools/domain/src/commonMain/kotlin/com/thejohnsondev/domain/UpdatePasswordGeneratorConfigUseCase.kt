package com.thejohnsondev.domain

import com.thejohnsondev.model.tools.PasswordGenerationType

interface UpdatePasswordGeneratorConfigUseCase {
    suspend operator fun invoke(
        type: PasswordGenerationType,
        length: Int,
        includeLower: Boolean,
        includeUpper: Boolean,
        includeDigits: Boolean,
        includeSpecial: Boolean
    )
}