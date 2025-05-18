package com.thejohnsondev.presentation.export

import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.ExportVaultUseCase
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class ExportPasswordsViewModel(
    private val passwordsService: PasswordsService,
    private val decryptPasswordsListUseCase: DecryptPasswordsListUseCase,
    private val exportVaultUseCase: ExportVaultUseCase
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
            Action.Clear -> clear()
            Action.Export -> export()
        }
    }

    private fun clear() {
        _state.update { State() }
    }

    private fun export() = launchLoading {
        val allPasswords = passwordsService.getUserPasswords().first()
        val decryptedPasswords = decryptPasswordsListUseCase(allPasswords)
        val exportResult = exportVaultUseCase.exportVault(decryptedPasswords)
        if (exportResult.success) {
            showContent()
            sendEvent(ExportSuccessfulEvent)
        } else {
            Logger.e("Export failed: ${exportResult.message}")
            showContent()
            sendEvent(ExportErrorEvent(message = DisplayableMessageValue.ExportUnsuccessful))
        }
    }

    data object ExportSuccessfulEvent: OneTimeEvent()
    data class ExportErrorEvent(val message: DisplayableMessageValue): OneTimeEvent()

    sealed class Action {
        data object Clear: Action()
        data object Export: Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
    )
}