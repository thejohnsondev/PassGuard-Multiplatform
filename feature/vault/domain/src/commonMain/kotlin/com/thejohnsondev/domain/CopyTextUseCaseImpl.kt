package com.thejohnsondev.domain

import com.thejohnsondev.data.VaultRepository

class CopyTextUseCaseImpl(
    private val vaultRepository: VaultRepository
): CopyTextUseCase {
    override fun invoke(text: String, isSensitive: Boolean) {
        vaultRepository.copyText(text, isSensitive)
    }
}