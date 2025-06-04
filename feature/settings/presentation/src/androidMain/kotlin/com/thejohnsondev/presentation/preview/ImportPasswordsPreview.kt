package com.thejohnsondev.presentation.preview

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.presentation.importv.ImportPasswordsScreenContent
import com.thejohnsondev.presentation.importv.ImportPasswordsViewModel
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImportPasswordsDefaultPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            ImportPasswordsScreenContent(
                state = ImportPasswordsViewModel.State(

                ),
                onAction = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImportPasswordsLoadingPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            ImportPasswordsScreenContent(
                state = ImportPasswordsViewModel.State(
                    screenState = ScreenState.Loading
                ),
                onAction = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImportPasswordsResultSuccessPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            ImportPasswordsScreenContent(
                state = ImportPasswordsViewModel.State(

                ),
                onAction = {}
            )
        }
    }
}