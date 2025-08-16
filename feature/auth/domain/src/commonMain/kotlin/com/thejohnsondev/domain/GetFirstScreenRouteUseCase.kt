package com.thejohnsondev.domain

import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.domain.repo.AuthRepository

class GetFirstScreenRouteUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(): Routes {
        val isUseBiometrics = authRepository.isUseBiometrics()

        return if (isVaultInitialized()) {
            if (isUseBiometrics) {
                Routes.BiometricRoute
            } else {
                Routes.HomeRoute()
            }
        } else {
            Routes.WelcomeRoute
        }
    }

    private suspend fun isVaultInitialized(): Boolean = authRepository.isVaultInitialized()
}