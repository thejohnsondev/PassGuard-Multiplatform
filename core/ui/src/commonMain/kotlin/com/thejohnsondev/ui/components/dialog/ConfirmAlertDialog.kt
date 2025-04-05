package com.thejohnsondev.ui.components.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.vector.ImageVector
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.designsystem.Percent70
import com.thejohnsondev.ui.utils.applyIf
import com.thejohnsondev.ui.utils.isCompact

@Composable
fun ConfirmAlertDialog(
    windowWidthSizeClass: WindowWidthSizeClass,
    icon: ImageVector = Icons.Default.Warning,
    title: String,
    message: String,
    confirmButtonText: String,
    cancelButtonText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .applyIf(!windowWidthSizeClass.isCompact()) {
                fillMaxWidth(Percent70)
            },
        icon = {
            Icon(imageVector = icon, null)
        },
        title = {
            Text(text = title)
        },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(modifier = Modifier.align(Alignment.Center), text = message)
            }
        },
        onDismissRequest = {
            onCancel()
        },
        confirmButton = {
            RoundedButton(
                text = confirmButtonText,
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
                text = cancelButtonText,
                onClick = {
                    onCancel()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    )
}