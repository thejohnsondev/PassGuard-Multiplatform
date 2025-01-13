package com.thejohnsondev.presentation.confirmdelete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.thejohnsondev.common.empty
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.model.validation.PasswordValidationState
import com.thejohnsondev.presentation.SettingsViewModel
import com.thejohnsondev.ui.components.RoundedButton
import com.thejohnsondev.ui.components.TextField
import com.thejohnsondev.ui.designsystem.Percent70
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.displaymessage.getAsComposeText
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.feature.settings.presentation.generated.resources.Res
import vaultmultiplatform.feature.settings.presentation.generated.resources.cancel
import vaultmultiplatform.feature.settings.presentation.generated.resources.confirm
import vaultmultiplatform.feature.settings.presentation.generated.resources.confirm_delete_account_with_password
import vaultmultiplatform.feature.settings.presentation.generated.resources.enter_password
import vaultmultiplatform.feature.settings.presentation.generated.resources.password

@Composable
fun DeleteAccountPasswordConfirmDialog(
    state: SettingsViewModel.State,
    onAction: (SettingsViewModel.Action) -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    val enteredPassword = rememberSaveable { mutableStateOf(String.Companion.empty) }
    AlertDialog(modifier = Modifier.applyIf(!windowWidthSizeClass.isCompact()) {
        fillMaxWidth(Percent70)
    }, icon = {
        Icon(imageVector = Icons.Default.Warning, null)
    }, title = {
        Text(text = stringResource(Res.string.enter_password))
    }, text = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = stringResource(Res.string.confirm_delete_account_with_password))
            TextField(
                modifier = Modifier.padding(vertical = Size8),
                textState = enteredPassword,
                onTextChanged = {
                    enteredPassword.value = it
                    onAction(SettingsViewModel.Action.DeleteAccountPasswordConfirmEntered(it))
                },
                label = stringResource(Res.string.password),
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                isError = state.deleteAccountPasswordConfirmValidationState !is PasswordValidationState.PasswordCorrectState,
                errorText = if (state.deleteAccountPasswordConfirmValidationState is PasswordValidationState.PasswordIncorrectState) {
                    state.deleteAccountPasswordConfirmValidationState.reason.getAsComposeText()
                } else null
            )
        }
    }, onDismissRequest = {
        onAction(SettingsViewModel.Action.CloseDeleteAccountPasswordConfirm)
    }, confirmButton = {
        RoundedButton(
            text = stringResource(Res.string.confirm),
            onClick = {
                onAction(SettingsViewModel.Action.DeleteAccountPasswordConfirm(enteredPassword.value))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            loading = state.screenState is ScreenState.Loading
        )
    }, dismissButton = {
        RoundedButton(
            text = stringResource(Res.string.cancel), onClick = {
                onAction(SettingsViewModel.Action.CloseDeleteAccountPasswordConfirm)
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    })
}