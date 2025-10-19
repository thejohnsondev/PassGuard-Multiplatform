package com.thejohnsondev.ui.designsystem

import android.content.Context
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

actual class DeviceThemeConfig(
    private val context: Context
) {
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    actual fun supportsDynamicTheming(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    @RequiresApi(Build.VERSION_CODES.S)
    actual fun getDynamicDarkColorScheme(): ColorScheme = dynamicDarkColorScheme(context)

    @RequiresApi(Build.VERSION_CODES.S)
    actual fun getDynamicLightColorScheme(): ColorScheme = dynamicLightColorScheme(context)

    actual fun supportsBlockingScreenshots(): Boolean = true
}

actual fun isBlurSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S