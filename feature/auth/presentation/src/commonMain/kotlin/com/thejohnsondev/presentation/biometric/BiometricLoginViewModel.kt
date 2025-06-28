package com.thejohnsondev.presentation.biometric

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.ShowBiometricPromptUseCase
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejosnsondev.biometric.BiometricAuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BiometricLoginViewModel(
    private val showBiometricPrompt: ShowBiometricPromptUseCase
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
            is Action.ShowLoginPrompt -> showLoginPrompt()
        }
    }

    private fun showLoginPrompt() = launch {
        val result = showBiometricPrompt(
            promptTitle = "Biometric Login",
            promptSubtitle = "Unlock with your biometrics",
            promptDescription = "Use your fingerprint or face recognition to unlock the app."
        )
        when (result) {
            is BiometricAuthResult.Success -> {
                sendEvent(OnLoginSuccess)
            }
            is BiometricAuthResult.Error -> {
                // Handle error, e.g., show a message to the user
            }

            BiometricAuthResult.Failed -> TODO()
            BiometricAuthResult.LockedOut -> TODO()
            BiometricAuthResult.NotAvailable -> TODO()
            BiometricAuthResult.UserCancelled -> TODO()
        }
    }

    data object OnLoginSuccess: OneTimeEvent()

    sealed class Action {
        data object ShowLoginPrompt : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
    )
}