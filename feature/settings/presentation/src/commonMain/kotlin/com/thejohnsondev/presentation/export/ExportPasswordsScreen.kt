package com.thejohnsondev.presentation.export

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.ui.components.button.BackArrowButton
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun ExportPasswordsScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowWidthSizeClass,
    sheetState: SheetState,
    viewModel: ExportPasswordsViewModel = koinViewModel<ExportPasswordsViewModel>(),
    onDismiss: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ModalBottomSheet(
        modifier = Modifier
            .applyIf(windowSizeClass.isCompact()) {
                systemBarsPadding()
            }.applyIf(!windowSizeClass.isCompact()) {
                padding(top = paddingValues.calculateTopPadding())
            },
        onDismissRequest = {
            viewModel.perform(ExportPasswordsViewModel.Action.Clear)
            onDismiss()
        },
        sheetState = sheetState,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        ),
        dragHandle = {
            ModalDragHandle(
                onDismissRequest = {
                    viewModel.perform(ExportPasswordsViewModel.Action.Clear)
                    onDismiss()
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            ScreenContent(
                state = state.value,
                onAction = viewModel::perform,
            )
        }
    }
}

// TODO extract ModalDragHandle to a separate file
@Composable
private fun ModalDragHandle(
    onDismissRequest: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Size16),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackArrowButton(
            modifier = Modifier.padding(start = Size16),
            onClick = {
                onDismissRequest()
            }
        )
    }
}

@Composable
private fun ScreenContent(
    state: ExportPasswordsViewModel.State,
    onAction: (ExportPasswordsViewModel.Action) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // TODO implement
    }
}