package com.thejohnsondev.domain

import com.thejohnsondev.data.ToolsRepository

class CopyTextUseCaseImpl(
    private val toolsRepository: ToolsRepository
): CopyTextUseCase {
    override fun invoke(text: String, isSensitive: Boolean) {
        toolsRepository.copyText(text, isSensitive)
    }
}