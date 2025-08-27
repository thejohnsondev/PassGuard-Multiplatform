package com.thejohnsondev.presentation.welcome

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.CheckInstallIDUseCase
import com.thejohnsondev.localization.Language
import com.thejohnsondev.localization.LocalizationUtils
import com.thejohnsondev.model.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class WelcomeViewModel(
    private val localizationUtils: LocalizationUtils,
    private val checkInstallIDUseCase: CheckInstallIDUseCase
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
            is Action.Setup -> setup()
            is Action.SelectLanguage -> selectLanguage(action.language)
            is Action.OpenCloseLanguageSelector -> openCloseLanguageSelector(action.isOpen)
        }
    }

    private fun setup() = launch {
        loadSelectedLanguage()
        checkInstallID()
    }

    private suspend fun loadSelectedLanguage()  {
        val selectedLanguage = localizationUtils.getSelectedLanguage()
        _state.update {
            it.copy(
                selectedLanguage = selectedLanguage
            )
        }
    }

    private suspend fun checkInstallID() {
        checkInstallIDUseCase()
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
        data object Setup : Action()
        data class OpenCloseLanguageSelector(
            val isOpen: Boolean
        ) : Action()
        data class SelectLanguage(val language: Language) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val selectedLanguage: Language? = null,
        val isLanguageSelectorOpen: Boolean = false
    )

}