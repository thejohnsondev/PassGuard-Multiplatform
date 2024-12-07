package com.thejohnsondev.domain

import com.thejohnsondev.common.navigation.Screens
import com.thejohnsondev.data.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetFirstScreenRouteUseCaseImplTest {

    private val authRepository = mockk<AuthRepository>()
    private val useCase = GetFirstScreenRouteUseCaseImpl(authRepository)

    @Test
    fun `returns HomeScreen when user is logged in and biometrics are not used`() = runTest {
        coEvery { authRepository.isUserLoggedIn() } returns true

        val result = useCase.invoke()

        assertEquals(Screens.HomeScreen.name, result)
    }

    @Test
    fun `returns BiometricScreen when user is logged in and biometrics are used`() = runTest {
        coEvery { authRepository.isUserLoggedIn() } returns true
        useCase.isUseBiometrics = true

        val result = useCase.invoke()

        assertEquals(Screens.BiometricScreen.name, result)
    }

    @Test
    fun `returns Welcome when user is not logged in`() = runTest {
        coEvery { authRepository.isUserLoggedIn() } returns false

        val result = useCase.invoke()

        assertEquals(Screens.Welcome.name, result)
    }
}