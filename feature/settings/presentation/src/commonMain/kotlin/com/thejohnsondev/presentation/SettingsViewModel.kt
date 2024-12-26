package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.AuthService
import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.IsBiometricsAvailableUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCase
import com.thejohnsondev.model.LoadingState
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

class SettingsViewModel(
    private val authService: AuthService,
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

    fun perform(action: Action) {

    }

    private fun logout() = launch {
        authService.logout()
    }

    private fun deleteAccount() = launch {
        authService.deleteAccount()
    }

    sealed class Action {
        data object FetchData : Action()
        data object Logout : Action()
        data object DeleteAccount : Action()
        data object OpenConfirmDeleteAccountDialog : Action()
        data object CloseConfirmDeleteAccountDialog : Action()
        data object OpenConfirmLogoutDialog : Action()
        data object CloseConfirmLogoutDialog : Action()
        data object OpenChangePasswordDialog : Action()
        data object CloseChangePasswordDialog : Action()
        data class UpdateUseCustomTheme(val customTheme: ThemeBrand) : Action()
        data class UpdateUseDynamicColor(val useDynamicColor: Boolean) : Action()
        data class UpdateDarkThemeConfig(val darkThemeConfig: DarkThemeConfig) : Action()
        data class UpdateGeneralSettings(val generalSettings: GeneralSettings) : Action()
        data class UpdatePrivacySettings(val privacySettings: PrivacySettings) : Action()
        data class UpdateExpandedSubSection(val section: SettingsSubSection) : Action()
        data class ChangePassword(
            val oldPassword: String,
            val newPassword: String
        ) : Action()

        data class ValidateNewPassword(val newPassword: String, val confirmPassword: String?) :
            Action()

        data class EnterConfirmPassword(
            val confirmPassword: String,
            val newPassword: String
        ) : Action()
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