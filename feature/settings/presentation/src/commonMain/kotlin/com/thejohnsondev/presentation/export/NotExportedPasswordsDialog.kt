package com.thejohnsondev.presentation.export

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItem
import com.thejohnsondev.ui.components.vault.passworditem.PasswordItemProperties
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
import com.thejohnsondev.ui.designsystem.Percent70
import com.thejohnsondev.ui.utils.ResString
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact
import org.jetbrains.compose.resources.stringResource
import vaultmultiplatform.core.ui.generated.resources.cancel
import vaultmultiplatform.core.ui.generated.resources.export_passwords_not_exported_confirm
import vaultmultiplatform.core.ui.generated.resources.export_passwords_not_exported_description
import vaultmultiplatform.core.ui.generated.resources.export_passwords_not_exported_title

@Composable
fun NotExportedPasswordsDialog(
    windowWidthSizeClass: WindowWidthSizeClass,
    notExportedPasswordsList: List<PasswordUIModel>,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier
            .applyIf(!windowWidthSizeClass.isCompact()) {
                fillMaxWidth(Percent70)
            },
        icon = {
            Icon(imageVector = Icons.Default.Warning, null)
        },
        title = {
            Text(text = stringResource(ResString.export_passwords_not_exported_title))
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = stringResource(ResString.export_passwords_not_exported_description))
                LazyColumn {
                    items(notExportedPasswordsList) { password ->
                        PasswordItem(
                            properties = PasswordItemProperties(
                                showFavoriteButton = false,
                                showCopyButton = false,
                                showEditButton = false,
                                showDeleteButton = false,
                                swapColorsWhenExpanding = false,
                                resizeCardWhenExpanded = false
                            ),
                            item = password,
                            onClick = { /* no-op */ },
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
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            RoundedButton(
                text = stringResource(ResString.export_passwords_not_exported_confirm),
                onClick = {
                    onConfirm()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            )
        },
        dismissButton = {
            RoundedButton(
                text = stringResource(ResString.cancel),
                onClick = {
                    onDismissRequest()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    )
}