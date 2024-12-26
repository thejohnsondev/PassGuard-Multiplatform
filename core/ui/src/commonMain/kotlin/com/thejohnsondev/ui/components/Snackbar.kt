package com.thejohnsondev.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorSuccessBackground
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorSuccessForeground
import com.thejohnsondev.ui.designsystem.getGlobalFontFamily

@Composable
fun SimpleSnackbar(
    modifier: Modifier = Modifier,
    message: String,
    messageColor: Color = MaterialTheme.colorScheme.primaryContainer,
    icon: ImageVector? = null,
    containerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    isRtl: Boolean = false,
) {
    Snackbar(
        modifier = modifier,
        containerColor = containerColor
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides
                    if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                icon?.let {
                    Icon(
                        modifier = Modifier.padding(end = Size8),
                        imageVector = icon,
                        contentDescription = null,
                        tint = messageColor
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = message,
                    color = messageColor,
                    fontFamily = getGlobalFontFamily(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun ErrorSnackbar(
    modifier: Modifier = Modifier,
    message: String
) {
    SimpleSnackbar(
        modifier = modifier,
        message = message,
        icon = Icons.Default.Error,
        messageColor = MaterialTheme.colorScheme.errorContainer,
        containerColor = MaterialTheme.colorScheme.onErrorContainer
    )
}

@Composable
fun InfoSnackbar(
    modifier: Modifier = Modifier,
    message: String
) {
    SimpleSnackbar(
        modifier = modifier,
        message = message,
        icon = Icons.Default.Info
    )
}

@Composable
fun SuccessSnackbar(
    modifier: Modifier = Modifier,
    message: String
) {
    SimpleSnackbar(
        modifier = modifier,
        message = message,
        icon = Icons.Default.Done,
        messageColor = themeColorSuccessForeground,
        containerColor = themeColorSuccessBackground
    )
}