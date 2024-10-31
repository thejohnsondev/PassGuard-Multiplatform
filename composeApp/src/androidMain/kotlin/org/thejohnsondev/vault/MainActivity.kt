package org.thejohnsondev.vault

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import org.koin.android.ext.android.getKoin
import org.thejohnsondev.vault.navigation.Root

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setupContent()
    }

    private fun setupContent() {
        val deviceThemeConfig: DeviceThemeConfig = getKoin().get()
        setContent {
            Root(deviceThemeConfig)
        }
    }

}