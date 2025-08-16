package com.thejohnsondev.presentation.welcome

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.localization.Language
import com.thejohnsondev.localization.LocalizationUtils
import com.thejohnsondev.model.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class WelcomeViewModel(
    private val localizationUtils: LocalizationUtils
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = combine(
        screenState,
        _state
    ) { screenState, state ->
        state.copy(
            screenState = screenState,
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, State())

    fun perform(action: Action) {
        when (action) {
            is Action.LoadSelectedLanguage -> loadSelectedLanguage()
            is Action.SelectLanguage -> selectLanguage(action.language)
            is Action.OpenLanguageSelector -> openCloseLanguageSelector(true)
        }
    }

    private fun loadSelectedLanguage() = launch {
        val selectedLanguage = localizationUtils.getSelectedLanguage()
        _state.update {
            it.copy(
                selectedLanguage = selectedLanguage
            )
        }
    }

    private fun selectLanguage(language: Language) = launch {
        _state.update {
            it.copy(
                selectedLanguage = language
            )
        }
        localizationUtils.setSelectedLanguage(language)
    }

    private fun openCloseLanguageSelector(isOpen: Boolean) {
        _state.update {
            it.copy(
                isLanguageSelectorOpen = isOpen
            )
        }
    }

    sealed class Action {
        data object LoadSelectedLanguage : Action()
        data object OpenLanguageSelector : Action()
        data class SelectLanguage(val language: Language) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val selectedLanguage: Language? = null,
        val isLanguageSelectorOpen: Boolean = false
    )

}