package com.thejohnsondev.settings

import com.thejohnsondev.data.SettingsRepository
import com.thejohnsondev.domain.UpdateSettingsUseCaseImpl
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.ThemeBrand
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class UpdateSettingsUseCaseImplTest {

    private val settingsRepository: SettingsRepository = mockk(relaxed = true)
    private val updateSettingsUseCase = UpdateSettingsUseCaseImpl(settingsRepository)

    @Test
    fun invokeUpdatesThemeBrandWhenProvided() = runBlocking {
        val themeBrand = ThemeBrand.DEFAULT

        updateSettingsUseCase.invoke(themeBrand, null, null, null, null)

        coVerify { settingsRepository.updateCustomTheme(themeBrand) }
    }

    @Test
    fun invokeUpdatesUseDynamicColorWhenProvided() = runBlocking {
        val useDynamicColor = true

        updateSettingsUseCase.invoke(null, useDynamicColor, null, null, null)

        coVerify { settingsRepository.updateUseDynamicColor(useDynamicColor) }
    }

    @Test
    fun invokeUpdatesDarkThemeConfigWhenProvided() = runBlocking {
        val darkThemeConfig = DarkThemeConfig.DARK

        updateSettingsUseCase.invoke(null, null, darkThemeConfig, null, null)

        coVerify { settingsRepository.updateDarkThemeConfig(darkThemeConfig) }
    }

    @Test
    fun invokeUpdatesGeneralSettingsWhenProvided() = runBlocking {
        val generalSettings = GeneralSettings(
            isDeepSearchEnabled = true
        )

        updateSettingsUseCase.invoke(null, null, null, generalSettings, null)

        coVerify { settingsRepository.updateGeneralSettings(generalSettings) }
    }

    @Test
    fun invokeUpdatesPrivacySettingsWhenProvided() = runBlocking {
        val privacySettings = PrivacySettings(
            isUnlockWithBiometricEnabled = true
        )

        updateSettingsUseCase.invoke(null, null, null, null, privacySettings)

        coVerify { settingsRepository.updatePrivacySettings(privacySettings) }
    }

    @Test
    fun invokeDoesNotUpdateWhenNoParametersProvided() = runBlocking {
        updateSettingsUseCase.invoke(null, null, null, null, null)

        coVerify(exactly = 0) { settingsRepository.updateCustomTheme(any()) }
        coVerify(exactly = 0) { settingsRepository.updateUseDynamicColor(any()) }
        coVerify(exactly = 0) { settingsRepository.updateDarkThemeConfig(any()) }
        coVerify(exactly = 0) { settingsRepository.updateGeneralSettings(any()) }
        coVerify(exactly = 0) { settingsRepository.updatePrivacySettings(any()) }
    }

}