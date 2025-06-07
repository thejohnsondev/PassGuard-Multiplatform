package com.thekohnsondev.ui.preview.container

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.ui.components.container.CsvTableDisplay
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CsvTableDisplayPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            CsvTableDisplay(
                csvContent = """
            name,url,username,password,note
            Google,https://google.com,user1,pass1,
        """.trimIndent(),
                errorValue = "user1"
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CsvTableDisplayOnlyHeadersPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            CsvTableDisplay(
                csvContent =   """
            name,url,username,password,note
        """.trimIndent()
            )
        }
    }
}