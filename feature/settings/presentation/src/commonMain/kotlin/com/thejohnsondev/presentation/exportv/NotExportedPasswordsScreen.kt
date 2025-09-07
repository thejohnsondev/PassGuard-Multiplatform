package com.thejohnsondev.presentation.exportv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.dialog.ModalDragHandle
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItem
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItemProperties
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import com.thejohnsondev.ui.designsystem.Percent100
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.core.ui.generated.resources.cancel
import vaultmultiplatform.core.ui.generated.resources.export_passwords_not_exported_confirm
import vaultmultiplatform.core.ui.generated.resources.export_passwords_not_exported_description

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun NotExportedPasswordsScreen(
    sheetState: SheetState,
    windowSizeClass: WindowWidthSizeClass,
    paddingValues: PaddingValues,
    viewModel: NotExportedPasswordsViewModel = koinViewModel(),
    notExportedPasswordsList: List<PasswordUIModel>,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.perform(
            NotExportedPasswordsViewModel.Action.SetPasswordList(
                notExportedPasswordsList
            )
        )
    }

    ModalBottomSheet(
        modifier = Modifier
            .applyIf(windowSizeClass.isCompact()) {
                systemBarsPadding()
            }.applyIf(!windowSizeClass.isCompact()) {
                padding(top = paddingValues.calculateTopPadding())
            },
        onDismissRequest = {
            viewModel.perform(NotExportedPasswordsViewModel.Action.Clear)
            onDismissRequest()
        },
        sheetState = sheetState,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        ),
        dragHandle = {
            ModalDragHandle(
                onDismissRequest = {
                    viewModel.perform(NotExportedPasswordsViewModel.Action.Clear)
                    onDismissRequest()
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        NotExportedPasswordsScreenContent(
            state = state.value,
            onAction = viewModel::perform,
            onConfirm = onConfirm,
            onDismissRequest = {
                viewModel.perform(NotExportedPasswordsViewModel.Action.Clear)
                onDismissRequest()
            }
        )
    }
}

@Composable
private fun NotExportedPasswordsScreenContent(
    state: NotExportedPasswordsViewModel.State,
    onAction: (NotExportedPasswordsViewModel.Action) -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(Size16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = stringResource(ResString.export_passwords_not_exported_description))
        LazyColumn(
            modifier = Modifier
                .padding(top = Size16)
                .weight(Percent100)
        ) {
            items(state.passwordList) { password ->
                PasswordItem(
                    properties = PasswordItemProperties(
                        showFavoriteButton = false,
                        showCopyButton = false,
                        showEditButton = false,
                        showDeleteButton = false,
                        swapColorsWhenExpanding = true,
                        resizeCardWhenExpanded = true
                    ),
                    item = password,
                    onClick = {
                        onAction(
                            NotExportedPasswordsViewModel.Action.ToggleOpenItem(
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
        Column(
            modifier = Modifier
                .padding(top = Size16)
        ) {
            RoundedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(ResString.export_passwords_not_exported_confirm),
                onClick = onConfirm
            )
            RoundedButton(
                modifier = Modifier
                    .padding(top = Size8)
                    .fillMaxWidth(),
                text = stringResource(ResString.cancel),
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}