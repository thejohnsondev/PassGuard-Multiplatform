package com.thejohnsondev.presentation.importv

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.model.DisplayableMessageValue
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.dialog.ModalDragHandle
import com.thejohnsondev.ui.components.loader.Loader
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.btn_select_csv
import vaultmultiplatform.core.ui.generated.resources.ic_import_colored
import vaultmultiplatform.core.ui.generated.resources.import_description
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
        )
    }
}

@Composable
fun ImportPasswordsScreenContent(
    state: ImportPasswordsViewModel.State,
    onAction: (ImportPasswordsViewModel.Action) -> Unit,
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
            state.csvParsingResult?.let {
                ImportResultContent(
                    state = state,
                    onAction = onAction
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
            contentDescription = "Export Icon",
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
            modifier = Modifier.padding(horizontal = Size16, bottom = Size32)
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
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // TODO implement
    }
}