package com.thejohnsondev.presentation.signup

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.combine
import com.thejohnsondev.domain.AuthService
import com.thejohnsondev.domain.EmailValidateUseCase
import com.thejohnsondev.domain.PasswordValidationUseCase
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.auth.AuthResponse
import com.thejohnsondev.model.validation.EmailValidationState
import com.thejohnsondev.model.validation.PasswordValidationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SignUpViewModel(
    private val authService: AuthService,
    private val emailValidateUseCase: EmailValidateUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase
) : BaseViewModel() {

    private val _isSignUpSuccess = MutableStateFlow<Boolean?>(null)
    private val _emailValidationState = MutableStateFlow<EmailValidationState?>(null)
    private val _passwordValidationState = MutableStateFlow<PasswordValidationState?>(null)
    private val _isPrivacyPolicyAccepted = MutableStateFlow(false)
    private val _signUpReadyState: Flow<Boolean> = combine(
        _emailValidationState,
        _passwordValidationState,
        _isPrivacyPolicyAccepted,
        ::isSignUpReady
    ).stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val viewState: Flow<State> = combine(
        _screenState,
        _isSignUpSuccess,
        _emailValidationState,
        _passwordValidationState,
        _signUpReadyState,
        _isPrivacyPolicyAccepted,
        ::State
    ).stateIn(viewModelScope, SharingStarted.Eagerly, State())

    fun perform(action: Action) {
        when (action) {
            is Action.SignUpWithEmail -> signUp(action.email, action.password)
            is Action.ValidateEmail -> validateEmail(action.email)
            is Action.ValidatePassword -> validatePassword(action.password)
            is Action.AcceptPrivacyPolicy -> acceptPrivacyPolicy(action.isAccepted)
        }
    }

    private fun acceptPrivacyPolicy(isAccepted: Boolean) = launch {
        _isPrivacyPolicyAccepted.value = isAccepted
    }

    private fun validateEmail(email: String) = launch {
        _emailValidationState.value = emailValidateUseCase.invoke(email)
    }

    private fun validatePassword(password: String) = launch {
        _passwordValidationState.value = passwordValidationUseCase.invoke(password)
    }

    private fun signUp(email: String, password: String) = launchLoading {
        authService.signUp(email, password).onResult {
            handleAuthResponse(it, email, password)
        }
    }

    private fun handleAuthResponse(
        authResponse: AuthResponse,
        email: String,
        password: String
    ) = launch {
        saveUserToken(authResponse.token)
        saveUserEmail(email)
        generateAndSaveEncryptionKey(password)
        sendEvent(OneTimeEvent.SuccessNavigation)
    }

    private fun saveUserEmail(email: String) = launch {
        authService.saveEmail(email)
    }

    private fun saveUserToken(token: String) = launch {
        authService.saveAuthToken(token)
    }

    private suspend fun generateAndSaveEncryptionKey(password: String) {
        authService.generateKey(password).onResult {
            handleGenerateKeySuccess(it)
        }
    }

    private fun handleGenerateKeySuccess(generatedKey: ByteArray) = launch {
        authService.saveKey(generatedKey)
    }

    private fun isSignUpReady(
        emailValidationState: EmailValidationState?,
        passwordValidationState: PasswordValidationState?,
        isPrivacyPolicyAccepted: Boolean?
    ): Boolean = emailValidationState is EmailValidationState.EmailCorrectState
            && passwordValidationState is PasswordValidationState.PasswordCorrectState
            && isPrivacyPolicyAccepted == true

    sealed class Action {
        class SignUpWithEmail(
            val email: String,
            val password: String
        ) : Action()
        class ValidateEmail(val email: String) : Action()
        class ValidatePassword(val password: String) : Action()
        class AcceptPrivacyPolicy(val isAccepted: Boolean) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val isSignUpSuccess: Boolean? = null,
        val emailValidationState: EmailValidationState? = null,
        val passwordValidationState: PasswordValidationState? = null,
        val signUpReady: Boolean = false,
        val isPrivacyPolicyAccepted: Boolean = false
    )

}