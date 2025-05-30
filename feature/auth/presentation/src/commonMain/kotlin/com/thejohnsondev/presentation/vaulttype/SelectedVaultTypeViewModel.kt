package com.thejohnsondev.presentation.vaulttype

import com.thejohnsondev.common.VAULT_GENERATION_FAKE_TIME_DURATION
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.AuthService
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.vault.VaultType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class SelectedVaultTypeViewModel(
    private val authService: AuthService,
) : BaseViewModel() {

    private val _selectedVaultType = MutableStateFlow<VaultType?>(null)

    val viewState = combine(
        screenState,
        _selectedVaultType,
        ::State
    )

    fun perform(action: Action) {
        when (action) {
            is Action.SelectVaultType -> onSelectVaultType(action.vaultType, action.isSelected)
            is Action.CreateLocalVault -> createLocalVault()
        }
    }

    private fun createLocalVault() = launchLoading {
        authService.generateSecretKey()
        delay(VAULT_GENERATION_FAKE_TIME_DURATION)
        sendEvent(NavigateToHome)
    }

    private fun onSelectVaultType(vaultType: VaultType, isSelected: Boolean) = launch {
        _selectedVaultType.value = if (isSelected) vaultType else null
    }

    data object NavigateToHome : OneTimeEvent()

    sealed class Action {
        data class SelectVaultType(val vaultType: VaultType, val isSelected: Boolean) : Action()
        data object CreateLocalVault : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val selectedVaultType: VaultType? = null,
    )

}