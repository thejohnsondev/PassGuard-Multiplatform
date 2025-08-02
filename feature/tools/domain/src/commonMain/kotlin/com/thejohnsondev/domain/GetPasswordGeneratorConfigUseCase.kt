package com.thejohnsondev.domain

import com.thejohnsondev.data.ToolsRepository
import com.thejohnsondev.model.tools.PasswordGeneratorConfig

class GetPasswordGeneratorConfigUseCase(
    private val toolsRepository: ToolsRepository,
) {
    suspend operator fun invoke(): PasswordGeneratorConfig {
        return toolsRepository.getPasswordGeneratorConfig()
    }
}