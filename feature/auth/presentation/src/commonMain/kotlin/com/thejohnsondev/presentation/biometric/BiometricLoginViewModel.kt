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
import kotlinx.coroutines.flow.update

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
            is Action.ShowLoginPrompt -> showLoginPrompt(
                title = action.title,
                subtitle = action.subtitle,
                description = action.description
            )
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
        data class ShowLoginPrompt(
            val title: String,
            val subtitle: String? = null,
            val description: String? = null,
        ) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val biometricLoginResult: LoginResult = LoginResult.NotStarted
    )
}

sealed class LoginResult {
    data object NotStarted : LoginResult()
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
    data object Failed : LoginResult()
}