package com.thejohnsondev.ui.model.message

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorSuccessBackground
import com.thejohnsondev.ui.designsystem.colorscheme.themeColorSuccessForeground

data class MessageColors(
    val messageColor: Color,
    val containerColor: Color,
) {
    companion object {

        @Composable
        fun getSuccessColors() = MessageColors(
            messageColor = themeColorSuccessForeground,
            containerColor = themeColorSuccessBackground,
        )

        @Composable
        fun getErrorColors() = MessageColors(
            messageColor = MaterialTheme.colorScheme.errorContainer,
            containerColor = MaterialTheme.colorScheme.onErrorContainer,
        )

        @Composable
        fun getInfoColors() = MessageColors(
            messageColor = MaterialTheme.colorScheme.primaryContainer,
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}