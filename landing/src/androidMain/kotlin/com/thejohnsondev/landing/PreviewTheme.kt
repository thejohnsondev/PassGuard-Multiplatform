package com.thejohnsondev.landing

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.thejohnsondev.common.model.settings.ThemeBrand
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme

@Composable
fun PreviewTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    VaultDefaultTheme(
        darkTheme = darkTheme,
        dynamicColor = false,
        customTheme = ThemeBrand.TEAL,
        deviceThemeConfig = DeviceThemeConfig(context),
        content = content
    )
}