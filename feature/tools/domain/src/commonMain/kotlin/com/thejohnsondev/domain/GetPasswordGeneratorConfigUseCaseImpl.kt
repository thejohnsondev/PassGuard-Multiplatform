package com.thejohnsondev.domain

import com.thejohnsondev.data.ToolsRepository
import com.thejohnsondev.model.tools.PasswordGeneratorConfig

class GetPasswordGeneratorConfigUseCaseImpl(
    private val toolsRepository: ToolsRepository,
): GetPasswordGeneratorConfigUseCase {
    override suspend fun invoke(): PasswordGeneratorConfig {
        return toolsRepository.getPasswordGeneratorConfig()
    }
}