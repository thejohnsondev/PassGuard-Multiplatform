package com.thejohnsondev.presentation.exportv

import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.ExportVaultUseCase
import com.thejohnsondev.domain.GenerateExportCSVUseCase
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCase
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.export.CSVGenerationResult
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class ExportPasswordsViewModel(
    private val passwordsService: PasswordsService,
    private val decryptPasswordsListUseCase: DecryptPasswordsListUseCase,
    private val generateExportCSVUseCase: GenerateExportCSVUseCase,
    private val passwordsMapToUiModelsUseCase: PasswordsMapToUiModelsUseCase,
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

    private var lastGeneratedCSVResult: CSVGenerationResult? = null

    fun perform(action: Action) {
        when (action) {
            Action.Clear -> clear()
            Action.Export -> export()
            Action.ExportProceedAnyway -> exportProceedAnyway()
        }
    }

    private fun clear() {
        _state.update { State() }
        lastGeneratedCSVResult = null
    }

    private fun export() = launchLoading {
        val allPasswords = passwordsService.getUserPasswords().first()
        val decryptedPasswords = decryptPasswordsListUseCase(allPasswords)
        val csvGenerationResult = generateExportCSVUseCase(decryptedPasswords)
        lastGeneratedCSVResult = csvGenerationResult

        if (csvGenerationResult.isSuccessful && csvGenerationResult.notExportedPasswords.isEmpty()) {
            exportCSVContent(csvGenerationResult.csvContent)
        } else {
            showNotExportedPasswords(csvGenerationResult)
        }
    }

    private suspend fun showNotExportedPasswords(csvGenerationResult: CSVGenerationResult) {
        val noExportedPasswords =
            passwordsMapToUiModelsUseCase(csvGenerationResult.notExportedPasswords)
        _state.update {
            it.copy(
                showNotExportedPasswordsBottomSheet = true,
                notExportedPasswords = noExportedPasswords
            )
        }
        showContent()
    }

    private fun exportProceedAnyway() = launchLoading {
        _state.update {
            it.copy(
                showNotExportedPasswordsBottomSheet = false,
                notExportedPasswords = null
            )
        }
        lastGeneratedCSVResult?.let {
            exportCSVContent(it.csvContent)
        } ?: run {
            showContent()
            sendEvent(ExportErrorEvent(message = DisplayableMessageValue.ExportUnsuccessful))
        }
    }

    private suspend fun exportCSVContent(csvContent: String) {
        // TODO handle cancelation
        exportVaultUseCase.exportVault(csvContent, onCompletion = { exportResult ->
            launch {
                if (exportResult.success) {
                    showContent()
                    sendEvent(ExportSuccessfulEvent)
                } else {
                    Logger.e("Export failed: ${exportResult.message}")
                    showContent()
                    sendEvent(ExportErrorEvent(message = DisplayableMessageValue.ExportUnsuccessful))
                }
            }
        })
    }

    data object ExportSuccessfulEvent: OneTimeEvent()
    data class ExportErrorEvent(val message: DisplayableMessageValue): OneTimeEvent()

    sealed class Action {
        data object Clear: Action()
        data object Export: Action()
        data object ExportProceedAnyway: Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val showNotExportedPasswordsBottomSheet: Boolean = false,
        val notExportedPasswords: List<PasswordUIModel>? = null,
    )
}