package com.thejohnsondev.domain

import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.data.AuthRepository

class GetFirstScreenRouteUseCaseImpl(
    private val authRepository: AuthRepository,
) : GetFirstScreenRouteUseCase {
    var isUseBiometrics = false // TODO Add later
    override suspend operator fun invoke(): String {

        return if (isUserLoggedIn()) {
            if (isUseBiometrics) {
                Screens.BiometricScreen.name
            } else {
                Screens.HomeScreen.name
            }
        } else {
            Screens.Welcome.name
        }
    }

    private suspend fun isUserLoggedIn(): Boolean = authRepository.isUserLoggedIn()
}