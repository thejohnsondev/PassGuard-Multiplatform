package com.thejohnsondev.presentation.preview

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.domain.export.FailedPasswordParsingEntry
import com.thejohnsondev.model.ScreenState
import com.thejohnsondev.presentation.importv.ImportPasswordsScreenContent
import com.thejohnsondev.presentation.importv.ImportPasswordsViewModel
import com.thejohnsondev.ui.components.vault.passworditem.PasswordUIModel
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
                    importResult = ImportPasswordsViewModel.ImportUIResult.ImportSuccess(
                        passwords = PasswordUIModel.testPasswordItems.take(4),
                        failedParsingEntries = listOf(
                            FailedPasswordParsingEntry(
                                lineNumber = 2,
                                rawLineContent = "example.com,username,password,notes",
                                reason = "Invalid password format. Password must be at least 8 characters long.",
                            ),
                            FailedPasswordParsingEntry(
                                lineNumber = 3,
                                rawLineContent = "example.com,username,password,notes",
                                reason = "Invalid username format. Username cannot be empty."
                            )
                        ),
                        dataLinesProcessed = 5
                    )
                ),
                onAction = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImportPasswordsResultEmptyPreview() {
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
                    importResult = ImportPasswordsViewModel.ImportUIResult.EmptyContent
                ),
                onAction = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImportPasswordsResultValidationErrorPreview() {
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
                    importResult = ImportPasswordsViewModel.ImportUIResult.ValidationError(
                        message = "Invalid CSV format",
                        rawContent = "example.com,username,password,notes\ninvalid,line,format",
                        details = "Line 2: Invalid password format. Password must be at least 8 characters long."
                    )
                ),
                onAction = {}
            )
        }
    }
}