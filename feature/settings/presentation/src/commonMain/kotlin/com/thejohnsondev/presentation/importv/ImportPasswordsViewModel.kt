package com.thejohnsondev.presentation.importv

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.domain.CheckPassDuplicatesUseCase
import com.thejohnsondev.domain.DecryptPasswordsListUseCase
import com.thejohnsondev.domain.EncryptPasswordModelUseCase
import com.thejohnsondev.domain.ParsePasswordsCSVUseCase
import com.thejohnsondev.domain.PasswordsMapToUiModelsUseCase
import com.thejohnsondev.domain.PasswordsService
import com.thejohnsondev.domain.SelectCSVUseCase
import com.thejohnsondev.domain.ToggleOpenedItemUseCase
import com.thejohnsondev.domain.export.CsvParsingResult
import com.thejohnsondev.domain.export.FailedPasswordParsingEntry
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.OneTimeEvent
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.platform.filemanager.FileActionStatus
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class ImportPasswordsViewModel(
    private val selectCSVUseCase: SelectCSVUseCase,
    private val parsePasswordsCSVUseCase: ParsePasswordsCSVUseCase,
    private val mapToUiModelsUseCase: PasswordsMapToUiModelsUseCase,
    private val toggleOpenedItemUseCase: ToggleOpenedItemUseCase,
    private val passwordsService: PasswordsService,
    private val encryptPasswordModelUseCase: EncryptPasswordModelUseCase,
    private val decryptPasswordsListUseCase: DecryptPasswordsListUseCase,
    private val checkPassDuplicatesUseCase: CheckPassDuplicatesUseCase
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
            is Action.Clear -> clear()
            is Action.SelectFile -> selectFile()
            is Action.ToggleOpenItem -> toggleOpenItem(action.itemId)
            is Action.Import -> import()
            is Action.ToggleSkipDuplicates -> toggleSkipDuplicates(action.isChecked)
        }
    }

    private fun toggleSkipDuplicates(isChecked: Boolean) = launch {
        _state.update { state ->
            state.copy(
                isSkipDuplicates = isChecked
            )
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
            showError("CSV file content is empty.")
            return@launch
        }
        withContext(Dispatchers.Default) {
            val parsedPasswordsResult = parsePasswordsCSVUseCase(csvContent)
            Logger.d("Parsed passwords result: $parsedPasswordsResult")
            val containsDuplicates = checkPassDuplicatesUseCase(
                savedPasswordsList = decryptPasswordsListUseCase(passwordsService.getUserPasswords().first()),
                newPasswordsList = (parsedPasswordsResult as? CsvParsingResult.Success)?.passwords ?: emptyList()
            )
            _state.update { state ->
                state.copy(
                    showSkipDuplicatesCheckBox = containsDuplicates.duplicates.isNotEmpty(),
                    isSkipDuplicates = true
                )
            }
            val successfullyParsedPasswords = mapToUiModelsUseCase(
                (parsedPasswordsResult as? CsvParsingResult.Success)?.passwords ?: emptyList()
            ).map {
                it.copy(showUpdateAnimation = false)
            }
            val importResult = when (parsedPasswordsResult) {
                is CsvParsingResult.EmptyContent -> ImportUIResult.EmptyContent
                is CsvParsingResult.Success -> ImportUIResult.ImportSuccess(
                    passwords = successfullyParsedPasswords,
                    failedParsingEntries = parsedPasswordsResult.failedEntries,
                    dataLinesProcessed = parsedPasswordsResult.summary.dataLinesProcessed
                )

                is CsvParsingResult.ValidationError -> ImportUIResult.ValidationError(
                    message = parsedPasswordsResult.message,
                    rawContent = parsedPasswordsResult.rawContent,
                    details = parsedPasswordsResult.details
                )
            }
            _state.update {
                it.copy(
                    importResult = importResult,
                    csvParsingResult = parsedPasswordsResult
                )
            }
            showContent()
        }
    }

    private fun toggleOpenItem(itemId: String?) = launch {
        val currentList = (_state.value.importResult as? ImportUIResult.ImportSuccess)?.passwords
        currentList?.let {
            val updatedList = toggleOpenedItemUseCase(itemId, listOf(currentList))
            _state.update { state ->
                state.copy(
                    importResult = (_state.value.importResult as ImportUIResult.ImportSuccess).copy(
                        passwords = updatedList.first()
                    )
                )
            }
        }
    }

    private fun import() = launchLoading {
        withContext(Dispatchers.Default) {
            try {
                val csvParsingResult = (_state.value.csvParsingResult as? CsvParsingResult.Success) ?: return@withContext
                var passwordsToImport = csvParsingResult.passwords
                if (_state.value.isSkipDuplicates) {
                    val savedDecryptedPasswords = decryptPasswordsListUseCase(passwordsService.getUserPasswords().first())
                    val checkPassDuplicatesResult = checkPassDuplicatesUseCase(
                        savedDecryptedPasswords,
                        passwordsToImport
                    )
                    passwordsToImport = checkPassDuplicatesResult.listWithoutDuplicates
                }
                passwordsToImport.forEach { password ->
                    val encrypted = encryptPasswordModelUseCase(password)
                    passwordsService.createOrUpdatePassword(encrypted)
                }
                showContent()
                sendEvent(ImportSuccessfulEvent)
            } catch (e: Exception) {
                sendEvent(ImportErrorEvent(DisplayableMessageValue.StringValue(e.message.orEmpty())))
            }
        }
    }

    data object ImportSuccessfulEvent: OneTimeEvent()
    data class ImportErrorEvent(val message: DisplayableMessageValue): OneTimeEvent()

    sealed class ImportUIResult {
        data class ImportSuccess(
            val passwords: List<PasswordUIModel>,
            val failedParsingEntries: List<FailedPasswordParsingEntry>,
            val dataLinesProcessed: Int,
        ) : ImportUIResult()

        data class ValidationError(
            val message: String,
            val rawContent: String,
            val details: String? = null,
        ) : ImportUIResult()

        data object EmptyContent : ImportUIResult()
    }

    sealed class Action {
        data object Clear : Action()
        data object SelectFile : Action()
        data class ToggleOpenItem(
            val itemId: String?,
        ) : Action()
        data object Import: Action()
        data class ToggleSkipDuplicates(
            val isChecked: Boolean
        ) : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.ShowContent,
        val importResult: ImportUIResult? = null,
        val csvParsingResult: CsvParsingResult? = null,
        val showSkipDuplicatesCheckBox: Boolean = false,
        val isSkipDuplicates: Boolean = false,
    )

}