package com.thejohnsondev.domain

import com.thejohnsondev.domain.repo.AuthRepository

class CheckInstallIDUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        val installID = authRepository.getInstallId()
        if (installID == null) {
            authRepository.generateAndSaveInstallId()
        }
        return true
    }
}