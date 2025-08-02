package com.thejohnsondev.domain

import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.data.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking

class GetFirstScreenRouteUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var useCase: GetFirstScreenRouteUseCase

    @BeforeTest
    fun setUp() {
        authRepository = mockk()
        useCase = GetFirstScreenRouteUseCase(authRepository)
    }

    @Test
    fun returnsWelcomeRouteWhenVaultNotInitialized() = runBlocking {
        coEvery { authRepository.isVaultInitialized() } returns false
        coEvery { authRepository.isUseBiometrics() } returns false
        val route = useCase.invoke()
        assertEquals(Routes.WelcomeRoute, route)
    }

    @Test
    fun returnsBiometricRouteWhenVaultInitializedAndUseBiometrics() = runBlocking {
        coEvery { authRepository.isVaultInitialized() } returns true
        coEvery { authRepository.isUseBiometrics() } returns true
        val route = useCase.invoke()
        assertEquals(Routes.BiometricRoute, route)
    }

    @Test
    fun returnsHomeRouteWhenVaultInitializedAndNotUseBiometrics() = runBlocking {
        coEvery { authRepository.isVaultInitialized() } returns true
        coEvery { authRepository.isUseBiometrics() } returns false
        val route = useCase.invoke()
        assertEquals(Routes.HomeRoute(), route)
    }
}