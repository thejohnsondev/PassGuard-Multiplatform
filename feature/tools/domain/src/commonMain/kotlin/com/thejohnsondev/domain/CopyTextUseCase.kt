package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.ToolsRepository

class CopyTextUseCase(
    private val toolsRepository: ToolsRepository
) {
    operator fun invoke(text: String, isSensitive: Boolean) {
        toolsRepository.copyText(text, isSensitive)
    }
}