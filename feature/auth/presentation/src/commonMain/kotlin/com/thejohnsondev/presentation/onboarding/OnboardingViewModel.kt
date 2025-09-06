package com.thejohnsondev.presentation.onboarding

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.analytics.Analytics
import com.thejohnsondev.common.VAULT_GENERATION_FAKE_TIME_DURATION
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.repo.AuthService
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.vault.VaultType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class OnboardingViewModel(
    private val authService: AuthService
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
            is Action.CreateLocalVault -> createLocalVault()
        }
    }

    private fun createLocalVault() = launchLoading {
        authService.generateSecretKey()
        delay(VAULT_GENERATION_FAKE_TIME_DURATION)
        Analytics.setVaultType(VaultType.LOCAL.name)
        Analytics.trackEvent("created_local_vault")
        sendEvent(NavigateToHome)
    }

    data object NavigateToHome : OneTimeEvent()

    sealed class Action {
        data object CreateLocalVault : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
    )
}