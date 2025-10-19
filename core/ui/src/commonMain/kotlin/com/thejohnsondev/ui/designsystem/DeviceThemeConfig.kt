package com.thejohnsondev.ui.designsystem

import androidx.compose.material3.ColorScheme

expect class DeviceThemeConfig {
    fun supportsDynamicTheming(): Boolean
    fun getDynamicDarkColorScheme(): ColorScheme
    fun getDynamicLightColorScheme(): ColorScheme
    fun supportsBlockingScreenshots(): Boolean
}

expect fun isBlurSupported(): Boolean