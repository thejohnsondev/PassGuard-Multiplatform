package com.thejohnsondev.ui.designsystem

import androidx.compose.material3.ColorScheme
import com.thejohnsondev.ui.designsystem.colorscheme.getDarkScheme
import com.thejohnsondev.ui.designsystem.colorscheme.getLightScheme

actual class DeviceThemeConfig {
    actual fun supportsDynamicTheming(): Boolean = false

    actual fun getDynamicDarkColorScheme(): ColorScheme = getDarkScheme()

    actual fun getDynamicLightColorScheme(): ColorScheme = getLightScheme()
    actual fun supportsBlockingScreenshots(): Boolean = false
}

actual fun isBlurSupported(): Boolean = true