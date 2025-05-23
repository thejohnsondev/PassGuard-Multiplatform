package com.thejohnsondev.presentation.export

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NotExportedPasswordsViewModel(
    private val toggleOpenedItemUseCase: ToggleOpenedItemUseCase
): BaseViewModel() {

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
            is Action.ToggleOpenItem -> toggleOpenItem(action.itemId)
            is Action.SetPasswordList -> setPasswordList(action.passwordList)
            is Action.Clear -> clear()
        }
    }

    private fun clear() {
        _state.update {
            State()
        }
    }

    private fun toggleOpenItem(itemId: String?) = launch {
        val currentList = _state.value.passwordList
        val updatedList = toggleOpenedItemUseCase(itemId, listOf(currentList))
        _state.update { state ->
            state.copy(
                passwordList = updatedList.first()
            )
        }
    }

    private fun setPasswordList(passwordList: List<PasswordUIModel>) {
        _state.update { state ->
            state.copy(
                passwordList = passwordList
            )
        }
    }

    sealed class Action {
        data class SetPasswordList(
            val passwordList: List<PasswordUIModel>,
        ) : Action()
        data class ToggleOpenItem(
            val itemId: String?,
        ) : Action()
        data object Clear: Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val passwordList: List<PasswordUIModel> = emptyList(),
    )
}