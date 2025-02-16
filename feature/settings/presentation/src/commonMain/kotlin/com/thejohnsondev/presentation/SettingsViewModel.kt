package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.AuthService
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.IsBiometricsAvailableUseCase
import com.thejohnsondev.domain.IsBlockingScreenshotAvailableUseCase
import com.thejohnsondev.domain.IsDynamicThemeAvailableUseCase
import com.thejohnsondev.domain.PasswordValidationUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCase
import com.thejohnsondev.model.Error
import com.thejohnsondev.model.LoadingState
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.auth.firebase.FBAuthSignInResponse
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
    private val isBiometricsAvailableUseCase: IsBiometricsAvailableUseCase,
    private val isDynamicThemeAvailableUseCase: IsDynamicThemeAvailableUseCase,
    private val isBlockingScreenshotAvailableUseCase: IsBlockingScreenshotAvailableUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase
) : BaseViewModel() {

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

    private fun fetchSettingsSections() = launch {
        val isLocalVault = authService.isVaultLocal()
        val settingsSection = if (isLocalVault) {
            SettingsSection.getLocalVaultSettingsSections()
        } else {
            SettingsSection.getCloudVaultSettingsSections()
        }
        _state.update { it.copy(settingsSection = settingsSection) }
    }

    fun perform(action: Action) {
        when (action) {
            is Action.FetchSettings -> fetchSettingsConfig()
            is Action.Logout -> logout()
            is Action.OpenConfirmLogoutDialog -> openCloseConfirmLogoutDialog(isOpen = true)
            is Action.CloseConfirmLogoutDialog -> openCloseConfirmLogoutDialog(isOpen = false)
            is Action.OpenConfirmDeleteAccountDialog -> openCloseConfirmDeleteAccountDialog(isOpen = true)
            is Action.CloseConfirmDeleteAccountDialog -> openCloseConfirmDeleteAccountDialog(isOpen = false)
            is Action.UpdateDarkThemeConfig -> updateDarkThemeConfig(action.darkThemeConfig)
            is Action.UpdateUseCustomTheme -> updateUseCustomTheme(action.customTheme)
            is Action.UpdateUseDynamicColor -> updateUseDynamicColor(action.useDynamicColor)
            is Action.UpdateGeneralSettings -> updateGeneralSettings(action.generalSettings)
            is Action.UpdatePrivacySettings -> updatePrivacySettings(action.privacySettings)
            is Action.OpenDeleteAccountPasswordConfirm -> openDeleteAccountPasswordConfirm(isOpen = true)
            is Action.CloseDeleteAccountPasswordConfirm -> openDeleteAccountPasswordConfirm(isOpen = false)
            is Action.DeleteAccountPasswordConfirmEntered -> onDeleteAccountPasswordConfirmEntered(
                action.password
            )

            is Action.DeleteAccountPasswordConfirm -> onDeleteAccountPasswordConfirm(action.password)
            is Action.OpenConfirmDeleteVaultDialog -> openCloseConfirmDeleteVaultDialog(isOpen = true)
            is Action.CloseConfirmDeleteVaultDialog -> openCloseConfirmDeleteVaultDialog(isOpen = false)
            is Action.DeleteLocalVaultConfirm -> deleteLocalVault()
        }
    }

    private fun openCloseConfirmDeleteVaultDialog(isOpen: Boolean) {
        _state.update { it.copy(isConfirmDeleteVaultDialogOpened = isOpen) }
    }

    private fun fetchSettingsConfig() = launch {
        val userEmail = getUserEmailUseCase()
//        val isBiometricsAvailable = isBiometricsAvailableUseCase() // todo uncomment after implementation
        val supportsDynamicTheming = isDynamicThemeAvailableUseCase()
        val supportsBlockingScreenshots = isBlockingScreenshotAvailableUseCase()
        getSettingsFlowUseCase.invoke().collect { config ->
            _state.update {
                it.copy(
                    settingsConfig = config,
                    userEmail = userEmail,
//                    isBiometricsAvailable = isBiometricsAvailable, // todo uncomment after implementation
                    supportsDynamicTheming = supportsDynamicTheming,
                    isBlockingScreenshotsAvailable = supportsBlockingScreenshots
                )
            }
        }
    }

    private fun openDeleteAccountPasswordConfirm(isOpen: Boolean) {
        _state.update { it.copy(isDeleteAccountPasswordConfirmDialogOpened = isOpen) }
    }

    private fun openCloseConfirmDeleteAccountDialog(isOpen: Boolean) {
        _state.update { it.copy(isConfirmDeleteAccountDialogOpened = isOpen) }
    }

    private fun openCloseConfirmLogoutDialog(isOpen: Boolean) {
        _state.update { it.copy(isConfirmLogoutDialogOpened = isOpen) }
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

    private fun onDeleteAccountPasswordConfirmEntered(password: String) = launch {
        val passwordValidationState = passwordValidationUseCase(password)
        _state.update { it.copy(deleteAccountPasswordConfirmValidationState = passwordValidationState) }
    }

    private fun onDeleteAccountPasswordConfirm(password: String) = launchLoading {
        val userEmail = getUserEmailUseCase()
        authService.signIn(
            email = userEmail,
            password = password
        ).onResult(
            onSuccess = ::handlePasswordEnteredCorrect,
            onError = ::handleDeleteAccountError
        )
    }

    private fun deleteLocalVault() = launch {
        logout()
    }

    private suspend fun handlePasswordEnteredCorrect(result: FBAuthSignInResponse) {
        safeLet(result.idToken, result.refreshToken) { idToken, refreshToken ->
            authService.saveAuthToken(idToken)
            authService.saveRefreshAuthToken(refreshToken)
            callDeleteAccount()
        }
    }


    private fun callDeleteAccount() = launch {
        authService.deleteAccount().onResult(
            onSuccess = {
                handleDeleteAccountSuccess()
            },
            onError = ::handleDeleteAccountError
        )
    }

    private fun handleDeleteAccountSuccess() = launch {
        openDeleteAccountPasswordConfirm(isOpen = false)
        showContent()
        logout()
    }

    private fun handleDeleteAccountError(error: Error) = launch {
        openDeleteAccountPasswordConfirm(isOpen = false)
        showContent()
        handleError(error)
    }

    private fun logout() = launch {
        authService.logout()
        sendEvent(OneTimeEvent.SuccessNavigation())
    }

    sealed class Action {
        data object FetchSettings : Action()
        data object Logout : Action()
        data object OpenConfirmDeleteAccountDialog : Action()
        data object CloseConfirmDeleteAccountDialog : Action()
        data object OpenConfirmLogoutDialog : Action()
        data object CloseConfirmLogoutDialog : Action()
        data object OpenConfirmDeleteVaultDialog : Action()
        data object CloseConfirmDeleteVaultDialog : Action()
        data class UpdateUseCustomTheme(val customTheme: ThemeBrand) : Action()
        data class UpdateUseDynamicColor(val useDynamicColor: Boolean) : Action()
        data class UpdateDarkThemeConfig(val darkThemeConfig: DarkThemeConfig) : Action()
        data class UpdateGeneralSettings(val generalSettings: GeneralSettings) : Action()
        data class UpdatePrivacySettings(val privacySettings: PrivacySettings) : Action()
        data object CloseDeleteAccountPasswordConfirm : Action()
        data object OpenDeleteAccountPasswordConfirm : Action()
        data class DeleteAccountPasswordConfirmEntered(val password: String) : Action()
        data class DeleteAccountPasswordConfirm(val password: String) : Action()
        data object DeleteLocalVaultConfirm : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val settingsSection: List<SettingsSection> = emptyList(),
        val userEmail: String? = null,
        val isConfirmDeleteAccountDialogOpened: Boolean = false,
        val isConfirmDeleteVaultDialogOpened: Boolean = false,
        val isConfirmLogoutDialogOpened: Boolean = false,
        val settingsConfig: SettingsConfig? = null,
        val updatePasswordLoadingState: LoadingState = LoadingState.Loaded,
        val openChangePasswordDialog: Boolean = false,
        val newPasswordValidationState: PasswordValidationState? = null,
        val isConfirmPasswordMatches: Boolean? = null,
        val isBiometricsAvailable: Boolean = false,
        val supportsDynamicTheming: Boolean = false,
        val isBlockingScreenshotsAvailable: Boolean = false,
        val isDeleteAccountPasswordConfirmDialogOpened: Boolean = false,
        val deleteAccountPasswordConfirmValidationState: PasswordValidationState? = null
    )

}