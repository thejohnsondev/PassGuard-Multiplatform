package com.thejohnsondev.domain

import com.thejohnsondev.data.ToolsRepository
import com.thejohnsondev.model.tools.PasswordGenerationType
import com.thejohnsondev.model.tools.PasswordGeneratorConfig

class UpdatePasswordGeneratorConfigUseCaseImpl(
    private val toolsRepository: ToolsRepository,
): UpdatePasswordGeneratorConfigUseCase {
    override suspend fun invoke(
        type: PasswordGenerationType,
        length: Int,
        includeLower: Boolean,
        includeUpper: Boolean,
        includeDigits: Boolean,
        includeSpecial: Boolean
    ) {
        toolsRepository.updatePasswordGeneratorConfig(
            PasswordGeneratorConfig(
                type = type,
                length = length,
                includeLower = includeLower,
                includeUpper = includeUpper,
                includeDigits = includeDigits,
                includeSpecial = includeSpecial
            )
        )
    }
}