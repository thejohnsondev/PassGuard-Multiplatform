package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.domain.AuthService
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.IsBiometricsAvailableUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCase
import com.thejohnsondev.model.LoadingState
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.settings.DarkThemeConfig
import com.thejohnsondev.model.settings.GeneralSettings
import com.thejohnsondev.model.settings.PrivacySettings
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.model.settings.ThemeBrand
import com.thejohnsondev.model.validation.PasswordValidationState
import com.thejohnsondev.ui.model.settings.SettingsSection
import com.thejohnsondev.ui.model.settings.SettingsSubSection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val authService: AuthService,
    private val getSettingsFlowUseCase: GetSettingsFlowUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val isBiometricsAvailableUseCase: IsBiometricsAvailableUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = combine(
        screenState,
        _state,
    ) { screenState, state ->
        state.copy(
            screenState = screenState
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, State())

    init {
        fetchSettingsSections()
    }

    private fun fetchSettingsSections() {
        _state.update { it.copy(settingsSection = SettingsSection.getSettingsSections()) }
    }

    fun perform(action: Action) {
        when (action) {
            is Action.FetchSettings -> fetchSettingsConfig()
            is Action.Logout -> logout()
            is Action.DeleteAccount -> deleteAccount()
            is Action.CloseConfirmDeleteAccountDialog -> closeConfirmDeleteAccountDialog()
            is Action.CloseConfirmLogoutDialog -> closeConfirmLogoutDialog()
            is Action.OpenConfirmDeleteAccountDialog -> openConfirmDeleteAccountDialog()
            is Action.OpenConfirmLogoutDialog -> openConfirmLogoutDialog()
            is Action.UpdateDarkThemeConfig -> updateDarkThemeConfig(action.darkThemeConfig)
            is Action.UpdateUseCustomTheme -> updateUseCustomTheme(action.customTheme)
            is Action.UpdateUseDynamicColor -> updateUseDynamicColor(action.useDynamicColor)
            is Action.UpdateGeneralSettings -> updateGeneralSettings(action.generalSettings)
            is Action.UpdatePrivacySettings -> updatePrivacySettings(action.privacySettings)
            is Action.UpdateExpandedSubSection -> updateExpandedSection(action.section)
        }
    }

    private fun fetchSettingsConfig() = launch {
        val userEmail = getUserEmailUseCase()
        getSettingsFlowUseCase.getSettingsConfigFlow().collect { config ->
            Logger.e("updated settings config: $config")
            _state.update {
                it.copy(
                    settingsConfig = config,
                    userEmail = userEmail
                )
            }
        }
    }

    private fun closeConfirmDeleteAccountDialog() {
        _state.update { it.copy(openConfirmDeleteAccountDialog = false) }
    }

    private fun closeConfirmLogoutDialog() {
        _state.update { it.copy(openConfirmLogoutDialog = false) }
    }

    private fun openConfirmDeleteAccountDialog() {
        _state.update { it.copy(openConfirmDeleteAccountDialog = true) }
    }

    private fun openConfirmLogoutDialog() {
        _state.update { it.copy(openConfirmLogoutDialog = true) }
    }

    private fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) = launch {
        updateSettingsUseCase(darkThemeConfig = darkThemeConfig)
    }

    private fun updateUseCustomTheme(customTheme: ThemeBrand) = launch {
        updateSettingsUseCase(themeBrand = customTheme)
    }

    private fun updateUseDynamicColor(useDynamicColor: Boolean) = launch {
        updateSettingsUseCase(useDynamicColor = useDynamicColor)
    }

    private fun updateGeneralSettings(generalSettings: GeneralSettings) = launch {
        updateSettingsUseCase(generalSettings = generalSettings)
    }

    private fun updatePrivacySettings(privacySettings: PrivacySettings) = launch {
        updateSettingsUseCase(privacySettings = privacySettings)
    }

    private fun updateExpandedSection(section: SettingsSubSection) {
        // TODO implement
    }

    private fun logout() = launch {
        authService.logout()
        sendEvent(OneTimeEvent.SuccessNavigation())
    }

    private fun deleteAccount() = launch {
        authService.deleteAccount().onResult {
            logout()
        }
    }

    sealed class Action {
        data object FetchSettings : Action()
        data object Logout : Action()
        data object DeleteAccount : Action()
        data object OpenConfirmDeleteAccountDialog : Action()
        data object CloseConfirmDeleteAccountDialog : Action()
        data object OpenConfirmLogoutDialog : Action()
        data object CloseConfirmLogoutDialog : Action()
        data class UpdateUseCustomTheme(val customTheme: ThemeBrand) : Action()
        data class UpdateUseDynamicColor(val useDynamicColor: Boolean) : Action()
        data class UpdateDarkThemeConfig(val darkThemeConfig: DarkThemeConfig) : Action()
        data class UpdateGeneralSettings(val generalSettings: GeneralSettings) : Action()
        data class UpdatePrivacySettings(val privacySettings: PrivacySettings) : Action()
        data class UpdateExpandedSubSection(val section: SettingsSubSection) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val settingsSection: List<SettingsSection> = emptyList(),
        val userEmail: String? = null,
        val openConfirmDeleteAccountDialog: Boolean = false,
        val openConfirmLogoutDialog: Boolean = false,
        val settingsConfig: SettingsConfig? = null,
        val updatePasswordLoadingState: LoadingState = LoadingState.Loaded,
        val openChangePasswordDialog: Boolean = false,
        val newPasswordValidationState: PasswordValidationState? = null,
        val isConfirmPasswordMatches: Boolean? = null,
        val isBiometricsAvailable: Boolean = false,
        val supportsDynamicTheming: Boolean = false // todo set from outside
    )

}