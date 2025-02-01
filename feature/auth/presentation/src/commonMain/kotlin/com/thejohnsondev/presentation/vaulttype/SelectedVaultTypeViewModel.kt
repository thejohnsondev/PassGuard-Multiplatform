package com.thejohnsondev.presentation.vaulttype

import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.model.vault.VaultType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class SelectedVaultTypeViewModel : BaseViewModel() {

    private val _selectedVaultType = MutableStateFlow<VaultType?>(null)

    val viewState = _selectedVaultType.map {
        State(selectedVaultType = it)
    }

    fun perform(action: Action) {
        when (action) {
            is Action.SelectVaultType -> onSelectVaultType(action.vaultType, action.isSelected)
        }
    }

    private fun onSelectVaultType(vaultType: VaultType, isSelected: Boolean) = launch {
        _selectedVaultType.value = if (isSelected) vaultType else null
    }

    sealed class Action {
        data class SelectVaultType(val vaultType: VaultType, val isSelected: Boolean) : Action()
    }

    data class State(
        val selectedVaultType: VaultType? = null,
    )

}