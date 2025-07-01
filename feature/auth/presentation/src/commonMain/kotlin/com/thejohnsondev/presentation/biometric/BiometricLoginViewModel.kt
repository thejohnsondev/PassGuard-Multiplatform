package com.thejohnsondev.presentation.biometric

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.domain.GetBiometricAvailabilityUseCase
import com.thejohnsondev.domain.ShowBiometricPromptUseCase
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejosnsondev.biometric.BiometricAuthResult
import com.thejosnsondev.biometric.BiometricAvailability
import com.thejosnsondev.biometric.BiometricType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class BiometricLoginViewModel(
    private val showBiometricPrompt: ShowBiometricPromptUseCase,
    private val getBiometricAvailabilityUseCase: GetBiometricAvailabilityUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = combine(
        screenState,
        _state,
    ) { screenState, state ->
        state.copy(screenState = screenState)
    }.stateIn(
        viewModelScope, SharingStarted.Eagerly,
        State()
    )

    fun perform(action: Action) {
        when (action) {
            is Action.GetBiometricAvailability -> getBiometricAvailability()
            is Action.ShowLoginPrompt -> showLoginPrompt(
                title = action.title,
                subtitle = action.subtitle,
                description = action.description
            )
        }
    }

    private fun getBiometricAvailability() = launch {
        _state.update { it.copy(screenState = ScreenState.Loading) }
        val availability = getBiometricAvailabilityUseCase()
        if (availability is BiometricAvailability.Available) {
            val biometricType = availability.type
            Logger.e("Biometric type available: $biometricType")
            _state.update {
                it.copy(
                    screenState = ScreenState.ShowContent,
                    biometricType = biometricType
                )
            }
        }
    }

    private fun showLoginPrompt(
        title: String,
        subtitle: String? = null,
        description: String? = null,
    ) = launch {
        val result = showBiometricPrompt(
            promptTitle = title,
            promptSubtitle = subtitle,
            promptDescription = description
        )
        when (result) {
            is BiometricAuthResult.Success -> {
                _state.update {
                    it.copy(
                        biometricLoginResult = LoginResult.Success
                    )
                }
                sendEvent(OnLoginSuccess)
            }

            is BiometricAuthResult.Error -> {
                result.message
                _state.update {
                    it.copy(
                        biometricLoginResult = LoginResult.Error(result.message)
                    )
                }
            }

            BiometricAuthResult.Failed -> {
                _state.update {
                    it.copy(
                        biometricLoginResult = LoginResult.Failed
                    )
                }
            }
        }
    }

    data object OnLoginSuccess : OneTimeEvent()

    sealed class Action {
        data object GetBiometricAvailability : Action()
        data class ShowLoginPrompt(
            val title: String,
            val subtitle: String? = null,
            val description: String? = null,
        ) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val biometricLoginResult: LoginResult = LoginResult.NotStarted,
        val biometricType: BiometricType? = null
    )
}

sealed class LoginResult {
    data object NotStarted : LoginResult()
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
    data object Failed : LoginResult()
}