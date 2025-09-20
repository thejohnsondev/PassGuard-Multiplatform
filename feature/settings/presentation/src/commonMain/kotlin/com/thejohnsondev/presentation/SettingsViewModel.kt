package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.CopyTextUseCase
import com.thejohnsondev.domain.repo.AuthService
import com.thejohnsondev.domain.GetBiometricAvailabilityUseCase
import com.thejohnsondev.domain.GetContactInfoUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.GetUserEmailUseCase
import com.thejohnsondev.domain.GetVersionInfoUseCase
import com.thejohnsondev.domain.IsBlockingScreenshotAvailableUseCase
import com.thejohnsondev.domain.IsDynamicThemeAvailableUseCase
import com.thejohnsondev.domain.PasswordValidationUseCase
import com.thejohnsondev.domain.UpdateSettingsUseCase
import com.thejohnsondev.domain.model.ContactInfo
import com.thejohnsondev.domain.model.VersionInfo
import com.thejohnsondev.localization.Language
import com.thejohnsondev.localization.LocalizationUtils
import com.thejohnsondev.model.DisplayableMessageValue
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
import com.thejosnsondev.biometric.BiometricAvailability
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
    private val getBiometricAvailabilityUseCase: GetBiometricAvailabilityUseCase,
    private val isDynamicThemeAvailableUseCase: IsDynamicThemeAvailableUseCase,
    private val isBlockingScreenshotAvailableUseCase: IsBlockingScreenshotAvailableUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val localizationUtils: LocalizationUtils,
    private val getVersionInfoUseCase: GetVersionInfoUseCase,
    private val getContactInfoUseCase: GetContactInfoUseCase,
    private val copyTextUseCase: CopyTextUseCase,
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
            is Action.OpenCloseExportPasswords -> openCloseExportPasswords(action.isOpen)
            is Action.OnExportSuccessful -> onExportSuccessful()
            is Action.OnExportError -> onExportError(action.message)
            is Action.OpenCloseImportPasswords -> openCloseImportPasswords(action.isOpen)
            is Action.OnImportSuccessful -> onImportSuccessful()
            is Action.OnImportError -> onImportError(action.message)
            is Action.SelectLanguage -> selectLanguage(action.language)
            is Action.OpenCloseVersionInfo -> openCloseVersionInfo(action.isOpen)
            is Action.OpenCloseLicenseInfo -> openCloseLicenseInfo(action.isOpen)
            is Action.OpenCloseContactInfo -> openCloseContactInfo(action.isOpen)
            is Action.CopyToClipboard -> copyToClipboard(action.text)
        }
    }

    private fun openCloseConfirmDeleteVaultDialog(isOpen: Boolean) {
        _state.update { it.copy(isConfirmDeleteVaultDialogOpened = isOpen) }
    }

    private fun fetchSettingsConfig() = launch {
        val userEmail = getUserEmailUseCase()
        val biometricAvailability = getBiometricAvailabilityUseCase()
        val supportsDynamicTheming = isDynamicThemeAvailableUseCase()
        val supportsBlockingScreenshots = isBlockingScreenshotAvailableUseCase()
        val versionInfo = getVersionInfoUseCase()
        val contactInfo = getContactInfoUseCase()
        getSettingsFlowUseCase.invoke().collect { config ->
            val selectedLanguage = localizationUtils.getSelectedLanguage()
            _state.update {
                it.copy(
                    settingsConfig = config,
                    userEmail = userEmail,
                    isBiometricsAvailable = biometricAvailability is BiometricAvailability.Available,
                    supportsDynamicTheming = supportsDynamicTheming,
                    isBlockingScreenshotsAvailable = supportsBlockingScreenshots,
                    selectedLanguage = selectedLanguage,
                    versionInfo = versionInfo,
                    contactInfo = contactInfo
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
        Analytics.trackEvent("updated_dark_theme_config", mapOf(
            "dark_theme_config" to darkThemeConfig.name
        ))
        Analytics.setAppTheme(darkThemeConfig.name)
    }

    private fun updateUseCustomTheme(customTheme: ThemeBrand) = launch {
        Analytics.trackEvent("updated_custom_theme", mapOf(
            "custom_theme" to customTheme.name
        ))
        updateSettingsUseCase(themeBrand = customTheme)
    }

    private fun updateUseDynamicColor(useDynamicColor: Boolean) = launch {
        Analytics.trackEvent("toggled_dynamic_color", mapOf(
            "use_dynamic_color" to useDynamicColor
        ))
        updateSettingsUseCase(useDynamicColor = useDynamicColor)
    }

    private fun updateGeneralSettings(generalSettings: GeneralSettings) = launch {
        Analytics.trackEvent("updated_general_settings", mapOf(
            "auto_lock_time" to generalSettings.isDeepSearchEnabled,
        ))
        updateSettingsUseCase(generalSettings = generalSettings)
    }

    private fun updatePrivacySettings(privacySettings: PrivacySettings) = launch {
        Analytics.trackEvent("updated_privacy_settings", mapOf(
            "is_block_screenshots_enabled" to privacySettings.isBlockScreenshotsEnabled,
            "is_biometric_authentication_enabled" to privacySettings.isUnlockWithBiometricEnabled,
        ))
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
        Analytics.trackEvent("deleted_local_vault")
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
        Analytics.trackEvent("deleted_account")
        openDeleteAccountPasswordConfirm(isOpen = false)
        showContent()
        logout()
    }

    private fun handleDeleteAccountError(error: Error) = launch {
        Analytics.trackEvent("delete_account_error", mapOf(
            "error" to error.toString()
        ))
        openDeleteAccountPasswordConfirm(isOpen = false)
        showContent()
        handleError(error)
    }

    private fun logout() = launch {
        Analytics.trackEvent("logged_out")
        authService.logout()
        Analytics.setVaultType(null)
        Analytics.setVaultInitialized(false)
        sendEvent(OneTimeEvent.SuccessNavigation())
    }

    private fun openCloseExportPasswords(isOpen: Boolean) {
        _state.update { it.copy(isExportPasswordsDialogOpened = isOpen) }
    }

    private fun onExportSuccessful() = launch {
        Analytics.trackEvent("exported_passwords")
        sendEvent(OneTimeEvent.InfoMessage(DisplayableMessageValue.ExportSuccessful))
    }

    private fun onExportError(message: DisplayableMessageValue) = launch {
        sendEvent(OneTimeEvent.ErrorMessage(message))
    }

    private fun openCloseImportPasswords(isOpen: Boolean) {
        _state.update { it.copy(isImportPasswordsDialogOpened = isOpen) }
    }

    private fun onImportSuccessful() = launch {
        Analytics.trackEvent("imported_passwords")
        sendEvent(OneTimeEvent.InfoMessage(DisplayableMessageValue.ImportSuccessful))
    }

    private fun onImportError(message: DisplayableMessageValue) = launch {
        sendEvent(OneTimeEvent.ErrorMessage(message))
    }

    private fun selectLanguage(language: Language) = launch {
        _state.update {
            it.copy(
                selectedLanguage = language
            )
        }
        Analytics.trackEvent("selected_language", mapOf(
            "language" to language.name
        ))
        localizationUtils.setSelectedLanguage(language)
    }

    private fun openCloseVersionInfo(isOpen: Boolean) {
        _state.update {
            it.copy(isVersionInfoOpened = isOpen)
        }
    }

    private fun openCloseLicenseInfo(isOpen: Boolean) {
        _state.update {
            it.copy(isLicenseInfoOpened = isOpen)
        }
    }

    private fun openCloseContactInfo(isOpen: Boolean) {
        _state.update {
            it.copy(isContactInfoOpened = isOpen)
        }
    }

    private fun copyToClipboard(text: String) = launch {
        copyTextUseCase(text, isSensitive = false)
        sendEvent(OneTimeEvent.InfoMessage(DisplayableMessageValue.Copied))
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
        data object OnExportSuccessful : Action()
        data class OnExportError(val message: DisplayableMessageValue) : Action()
        data class OpenCloseExportPasswords(val isOpen: Boolean) : Action()
        data class OpenCloseImportPasswords(val isOpen: Boolean) : Action()
        data object OnImportSuccessful : Action()
        data class OnImportError(val message: DisplayableMessageValue) : Action()
        data class SelectLanguage(val language: Language) : Action()
        data class OpenCloseVersionInfo(val isOpen: Boolean) : Action()
        data class OpenCloseLicenseInfo(val isOpen: Boolean) : Action()
        data class OpenCloseContactInfo(val isOpen: Boolean) : Action()
        data class CopyToClipboard(val text: String) : Action()
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
        val deleteAccountPasswordConfirmValidationState: PasswordValidationState? = null,
        val isExportPasswordsDialogOpened: Boolean = false,
        val isImportPasswordsDialogOpened: Boolean = false,
        val selectedLanguage: Language? = null,
        val isVersionInfoOpened: Boolean = false,
        val isLicenseInfoOpened: Boolean = false,
        val isContactInfoOpened: Boolean = false,
        val versionInfo: VersionInfo? = null,
        val contactInfo: ContactInfo? = null
    )

}