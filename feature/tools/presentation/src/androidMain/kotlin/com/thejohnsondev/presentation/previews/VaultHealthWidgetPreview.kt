package com.thejohnsondev.presentation.previews

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.domain.vaulthealth.VaultHealthReport
import com.thejohnsondev.model.vault.PasswordDto
import com.thejohnsondev.presentation.vaulthealth.VaultHealthWidgetContent
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme
import com.thejohnsondev.ui.utils.padding

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun VaultHealthWidgetGeneratedPreview() {
    VaultDefaultTheme(
        dynamicColor = false,
        darkTheme = true,
        deviceThemeConfig = null
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            VaultHealthWidgetContent(
                modifier = Modifier.padding(Size8)
                    .fillMaxWidth(),
                state = com.thejohnsondev.presentation.vaulthealth.VaultHealthViewModel.State(
                    report = VaultHealthReport(
                        overallScore = 0.95f,
                        totalPasswords = 128,
                        weakPasswords = listOf(
                            PasswordDto.demo1,
                            PasswordDto.demo1.copy(
                                title = "Demo Password 2"
                            )
                        ),
                        mediumPasswords = listOf(
                            PasswordDto.demo1.copy(
                                title = "Demo Password 3"
                            )
                        ),
                        strongPasswords = listOf(
                            PasswordDto.demo1.copy(
                                title = "Demo Password 4"
                            )
                        ),
                        leakedPasswords = listOf(
                            PasswordDto.demo1.copy(
                                title = "Demo Password 5"
                            )
                        ),
                        reusedPasswords = listOf(
                            PasswordDto.demo1.copy(
                                title = "Demo Password 6"
                            )
                        ),
                        oldPasswords = listOf(
                            PasswordDto.demo1.copy(
                                title = "Demo Password 7"
                            )
                        ),
                    )
                ),
                onAction = {},
            )
        }
    }
}