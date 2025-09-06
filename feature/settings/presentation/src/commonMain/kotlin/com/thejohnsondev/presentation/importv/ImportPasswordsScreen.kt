package com.thejohnsondev.presentation.importv

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.domain.export.CSVImportExportUtils
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.presentation.importv.ImportPasswordsViewModel.ImportSuccessfulEvent
import com.thejohnsondev.ui.components.ExpandableSectionItem
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.container.CsvTableDisplay
import com.thejohnsondev.ui.components.container.RoundedContainer
import com.thejohnsondev.ui.components.dialog.ModalDragHandle
import com.thejohnsondev.ui.components.loader.Loader
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItem
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItemProperties
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.themes.DeepForestSelectableItemColors
import com.thejohnsondev.ui.designsystem.colorscheme.selectableitemcolor.tools.ToolSelectableItemColors
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.btn_import
import vaultmultiplatform.core.ui.generated.resources.btn_select_another_csv
import vaultmultiplatform.core.ui.generated.resources.btn_select_csv
import vaultmultiplatform.core.ui.generated.resources.cancel
import vaultmultiplatform.core.ui.generated.resources.ic_empty_colored
import vaultmultiplatform.core.ui.generated.resources.ic_import_colored
import vaultmultiplatform.core.ui.generated.resources.ic_invalid_colored
import vaultmultiplatform.core.ui.generated.resources.import_description
import vaultmultiplatform.core.ui.generated.resources.import_empty_csv_description
import vaultmultiplatform.core.ui.generated.resources.import_empty_csv_title
import vaultmultiplatform.core.ui.generated.resources.import_invalid_csv_description
import vaultmultiplatform.core.ui.generated.resources.import_invalid_csv_sample_description
import vaultmultiplatform.core.ui.generated.resources.import_invalid_csv_title
import vaultmultiplatform.core.ui.generated.resources.import_skip_duplicates
import vaultmultiplatform.core.ui.generated.resources.import_successful_description
import vaultmultiplatform.core.ui.generated.resources.import_successful_failed_entries_description
import vaultmultiplatform.core.ui.generated.resources.import_successful_failed_entries_title
import vaultmultiplatform.core.ui.generated.resources.import_successful_failed_entry_line_number
import vaultmultiplatform.core.ui.generated.resources.import_successful_title
import vaultmultiplatform.core.ui.generated.resources.import_title

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun ImportPasswordsScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowWidthSizeClass,
    sheetState: SheetState,
    viewModel: ImportPasswordsViewModel = koinViewModel(),
    onImportSuccessful: () -> Unit,
    onImportError: (DisplayableMessageValue) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getEventFlow().collect {
            when (it) {
                ImportSuccessfulEvent -> onImportSuccessful()
                is ImportPasswordsViewModel.ImportErrorEvent -> onImportError(it.message)
            }
        }
    }

    ModalBottomSheet(
        modifier = Modifier.applyIf(windowSizeClass.isCompact()) {
            systemBarsPadding()
        }.applyIf(!windowSizeClass.isCompact()) {
            padding(top = paddingValues.calculateTopPadding())
        },
        onDismissRequest = {
            viewModel.perform(ImportPasswordsViewModel.Action.Clear)
            onDismissRequest()
        },
        sheetState = sheetState,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        ),
        dragHandle = {
            ModalDragHandle(
                onDismissRequest = {
                    viewModel.perform(ImportPasswordsViewModel.Action.Clear)
                    onDismissRequest()
                })
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        ImportPasswordsScreenContent(
            state = state.value,
            onAction = viewModel::perform,
            onCancelClick = {
                viewModel.perform(ImportPasswordsViewModel.Action.Clear)
                onDismissRequest()
            }
        )
    }
}

@Composable
fun ImportPasswordsScreenContent(
    state: ImportPasswordsViewModel.State,
    onAction: (ImportPasswordsViewModel.Action) -> Unit,
    onCancelClick: () -> Unit
) {

    when (state.screenState) {
        ScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Loader(
                    modifier = Modifier.size(Size32)
                        .align(Alignment.Center)
                )
            }
        }

        else -> {
            state.importResult?.let {
                ImportResultContent(
                    state = state,
                    onAction = onAction,
                    onCancelClick = onCancelClick
                )
            } ?: run {
                SelectCSVContent(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun SelectCSVContent(
    state: ImportPasswordsViewModel.State,
    onAction: (ImportPasswordsViewModel.Action) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier.padding(Size16).size(Size128),
            imageVector = vectorResource(ResDrawable.ic_import_colored),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(Size16),
            text = stringResource(ResString.import_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier.padding(top = Size4, horizontal = Size16),
            text = stringResource(ResString.import_description),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(Percent100))
        RoundedButton(
            modifier = Modifier
                .padding(horizontal = Size16, bottom = Size32)
                .fillMaxWidth(),
            text = stringResource(ResString.btn_select_csv),
            onClick = {
                onAction(ImportPasswordsViewModel.Action.SelectFile)
            },
            loading = state.screenState == ScreenState.Loading,
        )
    }
}

@Composable
private fun ImportResultContent(
    state: ImportPasswordsViewModel.State,
    onAction: (ImportPasswordsViewModel.Action) -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        when (state.importResult) {
            is ImportPasswordsViewModel.ImportUIResult.ImportSuccess -> {
                ImportSuccessContent(
                    successImportResult = state.importResult,
                    state = state,
                    onAction = onAction,
                    onCancelClick = onCancelClick
                )
            }

            is ImportPasswordsViewModel.ImportUIResult.EmptyContent -> {
                ImportEmptyContent(
                    onAction = onAction
                )
            }

            is ImportPasswordsViewModel.ImportUIResult.ValidationError -> {
                ImportErrorContent(
                    onAction = onAction,
                    onCancelClick = onCancelClick
                )
            }

            null -> {}
        }
    }
}

@Composable
private fun ColumnScope.ImportSuccessContent(
    state: ImportPasswordsViewModel.State,
    successImportResult: ImportPasswordsViewModel.ImportUIResult.ImportSuccess,
    onAction: (ImportPasswordsViewModel.Action) -> Unit,
    onCancelClick: () -> Unit
) {
    ExpandableSectionItem(
        modifier = Modifier
            .padding(horizontal = Size16, top = Size8)
            .fillMaxWidth(),
        title = stringResource(ResString.import_successful_title),
        description = stringResource(ResString.import_successful_description),
        isFirstItem = true,
        isLastItem = successImportResult.failedParsingEntries.isEmpty(),
        isOpenedByDefault = true,
        icon = Icons.Default.CheckCircle,
        colors = DeepForestSelectableItemColors
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = Size8)
        ) {
            successImportResult.passwords.forEach { password ->
                PasswordItem(
                    modifier = Modifier
                        .padding(horizontal = Size8),
                    properties = PasswordItemProperties(
                        showFavoriteButton = false,
                        showCopyButton = false,
                        showEditButton = false,
                        showDeleteButton = false,
                        swapColorsWhenExpanding = false,
                        resizeCardWhenExpanded = true
                    ),
                    item = password,
                    onClick = {
                        onAction(
                            ImportPasswordsViewModel.Action.ToggleOpenItem(
                                password.id
                            )
                        )
                    },
                    isExpanded = password.isExpanded,
                    onDeleteClick = { /* no-op */ },
                    onEditClick = { /* no-op */ },
                    onCopySensitive = { /* no-op */ },
                    onCopy = { /* no-op */ },
                    onFavoriteClick = { /* no-op */ },
                )
            }
        }
    }
    if (successImportResult.failedParsingEntries.isNotEmpty()) {
        ExpandableSectionItem(
            modifier = Modifier
                .padding(horizontal = Size16, top = Size4)
                .fillMaxWidth(),
            title = stringResource(ResString.import_successful_failed_entries_title),
            description = stringResource(ResString.import_successful_failed_entries_description),
            isFirstItem = false,
            isLastItem = true,
            icon = Icons.Default.Cancel,
            colors = ToolSelectableItemColors()
        ) {
            successImportResult.failedParsingEntries.forEach { failedEntry ->
                RoundedContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Size8)
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(horizontal = Size16, top = Size16),
                            text = stringResource(
                                ResString.import_successful_failed_entry_line_number,
                                failedEntry.lineNumber.toString()
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        CsvTableDisplay(
                            modifier = Modifier.padding(vertical = Size8, horizontal = Size16),
                            csvContent = failedEntry.rawLineContent,
                            errorValue = failedEntry.errorField
                        )
                        failedEntry.reason?.let { reason ->
                            Text(
                                modifier = Modifier.padding(bottom = Size16, horizontal = Size16),
                                text = reason,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
    Spacer(
        modifier = Modifier
            .weight(Percent100)
    )
    Column(
        modifier = Modifier
            .padding(Size16)
    ) {
        if (state.showSkipDuplicatesCheckBox) {
            Row(
                modifier = Modifier
                    .padding(vertical = Size8),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = state.isSkipDuplicates,
                    onCheckedChange = {
                        onAction(ImportPasswordsViewModel.Action.ToggleSkipDuplicates(it))
                    })
                Text(
                    modifier = Modifier.padding(start = Size8),
                    text = stringResource(ResString.import_skip_duplicates),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        RoundedButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(ResString.btn_import),
            onClick = {
                onAction(ImportPasswordsViewModel.Action.Import)
            }
        )
        RoundedButton(
            modifier = Modifier
                .padding(top = Size8)
                .fillMaxWidth(),
            text = stringResource(ResString.cancel),
            onClick = {
                onCancelClick()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }
}

@Composable
private fun ColumnScope.ImportEmptyContent(
    onAction: (ImportPasswordsViewModel.Action) -> Unit
) {
    Image(
        modifier = Modifier.padding(Size16).size(Size128),
        imageVector = vectorResource(ResDrawable.ic_empty_colored),
        contentDescription = null,
    )
    Text(
        modifier = Modifier.padding(Size16),
        text = stringResource(ResString.import_empty_csv_title),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        modifier = Modifier.padding(top = Size4, horizontal = Size16),
        text = stringResource(ResString.import_empty_csv_description),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.weight(Percent100))
    RoundedButton(
        modifier = Modifier
            .padding(horizontal = Size16, bottom = Size32)
            .fillMaxWidth(),
        text = stringResource(ResString.btn_select_another_csv),
        onClick = {
            onAction(ImportPasswordsViewModel.Action.SelectFile)
        },
    )
}

@Composable
private fun ColumnScope.ImportErrorContent(
    onAction: (ImportPasswordsViewModel.Action) -> Unit,
    onCancelClick: () -> Unit
) {
    Image(
        modifier = Modifier.padding(Size16).size(Size128),
        imageVector = vectorResource(ResDrawable.ic_invalid_colored),
        contentDescription = null,
    )
    Text(
        modifier = Modifier.padding(Size16),
        text = stringResource(ResString.import_invalid_csv_title),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        modifier = Modifier.padding(horizontal = Size16),
        text = stringResource(ResString.import_invalid_csv_description),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
    Text(
        modifier = Modifier.padding(top = Size32, horizontal = Size16),
        text = stringResource(ResString.import_invalid_csv_sample_description),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center
    )
    CsvTableDisplay(
        modifier = Modifier.padding(horizontal = Size16, top = Size16),
        csvContent = CSVImportExportUtils.getSampleCsvContent()
    )
    Spacer(modifier = Modifier.weight(Percent100))
    RoundedButton(
        modifier = Modifier
            .padding(horizontal = Size16)
            .fillMaxWidth(),
        text = stringResource(ResString.btn_select_another_csv),
        onClick = {
            onAction(ImportPasswordsViewModel.Action.SelectFile)
        }
    )
    RoundedButton(
        modifier = Modifier
            .padding(top = Size8, bottom = Size32, horizontal = Size16)
            .fillMaxWidth(),
        text = stringResource(ResString.cancel),
        onClick = {
            onCancelClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}