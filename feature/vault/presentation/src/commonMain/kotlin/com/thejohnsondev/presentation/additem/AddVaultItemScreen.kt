package com.thejohnsondev.presentation.additem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.thejohnsondev.ui.components.HintTextField
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.Text22
import com.thejohnsondev.uimodel.models.PasswordUIModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import vaultmultiplatform.feature.vault.presentation.generated.resources.Res
import vaultmultiplatform.feature.vault.presentation.generated.resources.organization

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun AddVaultItemScreen(
    paddingValues: PaddingValues,
    sheetState: SheetState,
    viewModel: AddVaultItemViewModel = koinViewModel<AddVaultItemViewModel>(),
    vaultItem: PasswordUIModel? = null,
    onDismissRequest: () -> Unit
) {
    val state = viewModel.state.collectAsState(AddVaultItemViewModel.State())
    ModalBottomSheet(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding()),
        sheetState = sheetState,
        onDismissRequest = {
            viewModel.clear()
            onDismissRequest()
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            AddPasswordContent(
                state = state.value,
                vaultItem = vaultItem,
                onAction = viewModel::perform
            )
        }
    }
}

@Composable
fun AddPasswordContent(
    state: AddVaultItemViewModel.State,
    vaultItem: PasswordUIModel?,
    onAction: (AddVaultItemViewModel.Action) -> Unit
) {
    LaunchedEffect(true) {
        if (vaultItem != null) {
            onAction(AddVaultItemViewModel.Action.SetPasswordForEdit(vaultItem))
        }
    }
    HintTextField(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = Size16),
        onValueChanged = { organization ->
            onAction(AddVaultItemViewModel.Action.EnterOrganization(organization))
        },
        value = state.organization,
        hint = stringResource(Res.string.organization),
        textColor = MaterialTheme.colorScheme.onSurface,
        fontSize = Text22,
        maxLines = 2,
        onKeyboardAction = {
            // todo call focus on next field
        },
        imeAction = ImeAction.Next
    )

}