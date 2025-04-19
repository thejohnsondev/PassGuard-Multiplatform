package com.thejohnsondev.presentation.previews

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.model.tools.PasswordGeneratedResult
import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorViewModel
import com.thejohnsondev.presentation.passwordgenerator.PasswordGeneratorWidgetContent
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PasswordGeneratorWidgetGeneratedPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            PasswordGeneratorWidgetContent(
                modifier = Modifier.fillMaxWidth(),
                state = PasswordGeneratorViewModel.State(
                    passwordGeneratedResult = PasswordGeneratedResult(
                        password = "Qbegfdjl3lk49@#",
                        strengthLevel = 0.6f,
                        suggestion = "Make a stronger password",
                    )
                ),
                onAction = {},
                showConfigureByDefault = false
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PasswordGeneratorWidgetGeneratedLongPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            PasswordGeneratorWidgetContent(
                modifier = Modifier.fillMaxWidth(),
                state = PasswordGeneratorViewModel.State(
                    passwordGeneratedResult = PasswordGeneratedResult(
                        password = "Qbegfdjl3lk49@#fdsjjhfsdhfdsjfsd",
                        strengthLevel = 0.3f,
                        suggestion = "Make a stronger password, Make a stronger password, Make a stronger password",
                    )
                ),
                onAction = {},
                showConfigureByDefault = true
            )
        }
    }
}