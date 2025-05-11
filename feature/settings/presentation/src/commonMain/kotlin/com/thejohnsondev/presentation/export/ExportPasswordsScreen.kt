package com.thejohnsondev.presentation.export

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.ui.components.button.BackArrowButton
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size24
import com.thejohnsondev.ui.designsystem.Size32
import com.thejohnsondev.ui.designsystem.Size4
import com.thejohnsondev.ui.designsystem.Size86
import com.thejohnsondev.ui.utils.ResDrawable
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import com.thejohnsondev.ui.utils.padding
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.btn_export
import vaultmultiplatform.core.ui.generated.resources.export_description
import vaultmultiplatform.core.ui.generated.resources.export_title
import vaultmultiplatform.core.ui.generated.resources.export_warning
import vaultmultiplatform.core.ui.generated.resources.ic_export_colored

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun ExportPasswordsScreen(
    paddingValues: PaddingValues,
    windowSizeClass: WindowWidthSizeClass,
    sheetState: SheetState,
    viewModel: ExportPasswordsViewModel = koinViewModel<ExportPasswordsViewModel>(),
    onExportSuccessful: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getEventFlow().collect {
            when (it) {
                is ExportPasswordsViewModel.ExportSuccessfulEvent -> {
                    onExportSuccessful()
                }
            }
        }
    }

    ModalBottomSheet(
        modifier = Modifier
            .applyIf(windowSizeClass.isCompact()) {
                systemBarsPadding()
            }.applyIf(!windowSizeClass.isCompact()) {
                padding(top = paddingValues.calculateTopPadding())
            },
        onDismissRequest = {
            viewModel.perform(ExportPasswordsViewModel.Action.Clear)
            onDismissRequest()
        },
        sheetState = sheetState,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        ),
        dragHandle = {
            ModalDragHandle(
                onDismissRequest = {
                    viewModel.perform(ExportPasswordsViewModel.Action.Clear)
                    onDismissRequest()
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        ExportPasswordsScreenContent(
            state = state.value,
            onAction = viewModel::perform,
        )
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
fun ExportPasswordsScreenContent(
    state: ExportPasswordsViewModel.State,
    onAction: (ExportPasswordsViewModel.Action) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .padding(Size16)
                .size(Size128),
            imageVector = vectorResource(ResDrawable.ic_export_colored),
            contentDescription = "Export Icon",
        )
        Text(
            modifier = Modifier.padding(Size16),
            text = stringResource(ResString.export_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier.padding(top = Size4, horizontal = Size16),
            text = stringResource(ResString.export_description),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(Percent100))
        Row(
            modifier = Modifier
                .padding(Size16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(Size24),
                imageVector = Icons.Default.Warning,
                contentDescription = "Warning Icon",
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                modifier = Modifier
                    .padding(start = Size16)
                    .weight(1f),
                text = stringResource(ResString.export_warning),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Start
            )
        }
        RoundedButton(
            modifier = Modifier
                .padding(horizontal = Size16, bottom = Size32)
                .fillMaxWidth(),
            text = stringResource(ResString.btn_export),
            onClick = {
                onAction(ExportPasswordsViewModel.Action.Export)
            },
            loading = state.screenState == ScreenState.Loading,
        )

    }
}