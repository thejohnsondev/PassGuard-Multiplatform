package com.thejohnsondev.ui.designsystem

import androidx.compose.material3.ColorScheme
import com.thejohnsondev.ui.designsystem.colorscheme.darkScheme
import com.thejohnsondev.ui.designsystem.colorscheme.lightScheme

actual class DeviceThemeConfig {
    actual fun supportsDynamicTheming(): Boolean = false

    actual fun getDynamicDarkColorScheme(): ColorScheme = darkScheme

    actual fun getDynamicLightColorScheme(): ColorScheme = lightScheme
}

actual val showNavigationBackArrow: Boolean = true