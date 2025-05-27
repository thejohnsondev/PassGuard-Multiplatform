package com.thejohnsondev.presentation.preview

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.presentation.exportv.ExportPasswordsScreenContent
import com.thejohnsondev.presentation.exportv.ExportPasswordsViewModel
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExportPasswordsPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            ExportPasswordsScreenContent(
                state = ExportPasswordsViewModel.State(),
                onAction = {}
            )
        }
    }
}