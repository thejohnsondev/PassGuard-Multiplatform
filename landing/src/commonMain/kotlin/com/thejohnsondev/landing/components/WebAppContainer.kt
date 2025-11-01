package com.thejohnsondev.landing.components

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

object WebAppContainer {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(scope, SharingStarted.Eagerly, State())

    fun performAction(action: Action) {
        when (action) {
            is Action.ToggleDarkTheme -> toggleDarkTheme()
        }
    }

    private fun toggleDarkTheme() {
        _state.update {
            it.copy(
                isDarkTheme = !_state.value.isDarkTheme
            )
        }
    }

    sealed class Action {
        data object ToggleDarkTheme : Action()
    }

    data class State(
        val isDarkTheme: Boolean = false,
    )

}