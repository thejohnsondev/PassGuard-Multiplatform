package com.thejohnsondev.presentation.export

import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.model.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class ExportPasswordsViewModel : BaseViewModel() {

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
        }
    }

    private fun clear() {
        _state.update { State() }
    }

    sealed class Action {
        data object Clear: Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
    )
}