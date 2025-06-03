package com.thejohnsondev.presentation.importv

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.domain.ParsePasswordsCSVUseCase
import com.thejohnsondev.domain.SelectCSVUseCase
import com.thejohnsondev.domain.export.CsvParsingResult
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.platform.filemanager.FileActionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ImportPasswordsViewModel(
    private val selectCSVUseCase: SelectCSVUseCase,
    private val parsePasswordsCSVUseCase: ParsePasswordsCSVUseCase
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
            Action.Clear -> clear()
            Action.SelectFile -> selectFile()
        }
    }

    private fun clear() {
        _state.update { State() }
    }

    private fun selectFile() {
        // TODO implement
    private fun selectFile() = launch {
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
        // TODO implement
    }

    private fun onFileSelectionError(errorMessage: String) = launch {
        // TODO implement
    }

    private fun onCSVFileSelected(
        csvContent: String?,
    ) = launch {
        csvContent ?: run {
            // TODO show error
            return@launch
        }
        val parsedPasswordsResult = parsePasswordsCSVUseCase(csvContent)
        when (parsedPasswordsResult) {
            is CsvParsingResult.EmptyContent -> {
                // TODO show empty content state
            }
            is CsvParsingResult.Success -> {
                // TODO show success with parsed and unparsed passwords and other data
            }
            is CsvParsingResult.ValidationError -> {
                // TODO show validation error with details
            }
        }
    }

    sealed class Action {
        data object Clear: Action()
        data object SelectFile: Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
    )

}