package com.thejohnsondev.ui.designsystem

import androidx.compose.material3.ColorScheme

actual class DeviceThemeConfig {
    actual fun supportsDynamicTheming(): Boolean = false

    actual fun getDynamicDarkColorScheme(): ColorScheme = DarkColors

    actual fun getDynamicLightColorScheme(): ColorScheme = LightColors
}