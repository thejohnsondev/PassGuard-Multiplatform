package com.thejohnsondev.presentation.importv

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.ParsePasswordsCSVUseCase
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCase
import com.thejohnsondev.domain.SelectCSVUseCase
import com.thejohnsondev.domain.export.CsvParsingResult
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.platform.filemanager.FileActionStatus
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ImportPasswordsViewModel(
    private val selectCSVUseCase: SelectCSVUseCase,
    private val parsePasswordsCSVUseCase: ParsePasswordsCSVUseCase,
    private val mapToUiModelsUseCase: PasswordsMapToUiModelsUseCase
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
            Action.SelectFile -> selectFile()
        }
    }

    private fun clear() {
        _state.update { State() }
    }

    private fun selectFile() = launchLoading {
        selectCSVUseCase(
            onCompletion = { importResult ->
                when (importResult.status) {
                    FileActionStatus.SUCCESS -> onCSVFileSelected(importResult.fileContent)
                    FileActionStatus.FAILURE -> onFileSelectionError(importResult.message)
                    FileActionStatus.CANCELED -> onFileSelectionCanceled()
                }
            }
        )

    }

    private fun onFileSelectionCanceled() = launch {
        showContent()
    }

    private fun onFileSelectionError(errorMessage: String) = launch {
        showError(errorMessage)
    }

    private fun onCSVFileSelected(
        csvContent: String?,
    ) = launch {
        csvContent ?: run {
            showError("CSV file content is empty.") // TODO extract to resources
            return@launch
        }
        val parsedPasswordsResult = parsePasswordsCSVUseCase(csvContent)
        val successfullyParsedPasswords = mapToUiModelsUseCase(
            (parsedPasswordsResult as? CsvParsingResult.Success)?.passwords ?: emptyList()
        )
        _state.update {
            it.copy(
                successfullyParsedPasswords = successfullyParsedPasswords,
                csvParsingResult = parsedPasswordsResult
            )
        }
        showContent()
    }

    sealed class Action {
        data object Clear : Action()
        data object SelectFile : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val successfullyParsedPasswords: List<PasswordUIModel>? = null,
        val csvParsingResult: CsvParsingResult? = null
    )

}