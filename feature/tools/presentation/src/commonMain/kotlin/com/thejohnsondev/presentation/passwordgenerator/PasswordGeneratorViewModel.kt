package com.thejohnsondev.presentation.passwordgenerator

import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.GeneratePasswordUseCase
import com.thejohnsondev.domain.GetPasswordGeneratorConfigUseCase
import com.thejohnsondev.domain.UpdatePasswordGeneratorConfigUseCase
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.tools.PasswordGenerationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine

class PasswordGeneratorViewModel(
    private val updatePasswordGeneratorConfigUseCase: UpdatePasswordGeneratorConfigUseCase,
    private val getPasswordGeneratorConfigUseCase: GetPasswordGeneratorConfigUseCase,
    private val generatePasswordUseCase: GeneratePasswordUseCase
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

            else -> {}
        }
    }

    sealed class Action {
        data object GeneratePassword : Action()
        data class UpdateLength(val length: Int): Action()
        data class UpdateIncludeLower(val includeLower: Boolean): Action()
        data class UpdateIncludeUpper(val includeUpper: Boolean): Action()
        data class UpdateIncludeDigits(val includeDigits: Boolean): Action()
        data class UpdateIncludeSpecial(val includeSpecial: Boolean): Action()
        data class UpdateType(val type: PasswordGenerationType): Action()
        data object Reset : Action()
        data object Copy : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
    )
}