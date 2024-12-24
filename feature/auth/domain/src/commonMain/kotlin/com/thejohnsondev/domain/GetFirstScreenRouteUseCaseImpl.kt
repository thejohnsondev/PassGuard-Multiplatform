package com.thejohnsondev.domain

import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.data.AuthRepository

class GetFirstScreenRouteUseCaseImpl(
    private val authRepository: AuthRepository,
) : GetFirstScreenRouteUseCase {
    var isUseBiometrics = false // TODO Add later
    override suspend operator fun invoke(): Routes {

        return if (isUserLoggedIn()) {
            if (isUseBiometrics) {
                Routes.BiometricRoute
            } else {
                Routes.HomeRoute
            }
        } else {
            Routes.WelcomeRoute
        }
    }

    private suspend fun isUserLoggedIn(): Boolean = authRepository.isUserLoggedIn()
}