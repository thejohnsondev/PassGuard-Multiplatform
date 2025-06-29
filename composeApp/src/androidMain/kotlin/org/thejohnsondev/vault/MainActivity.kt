package org.thejohnsondev.vault

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.platform.filemanager.AndroidActivityProvider
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.thejohnsondev.vault.root.Root
import org.thejohnsondev.vault.root.shouldUseDarkTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        AndroidActivityProvider.registerFilePicker(this)
        val getFirstScreenRoute = getKoin().get<GetFirstScreenRouteUseCase>()
        val getSettingsUseCase = getKoin().get<GetSettingsFlowUseCase>()
        val deviceThemeConfig: DeviceThemeConfig = getKoin().get()
        val firstScreenRoute = mutableStateOf<Routes?>(null)
        val settingsConfig = mutableStateOf<SettingsConfig?>(null)
        lifecycleScope.launch {
            firstScreenRoute.value = getFirstScreenRoute()
        }
        lifecycleScope.launch {
            getSettingsUseCase.invoke().collect {
                settingsConfig.value = it
                applyPrivacySettings(it.privacySettings.isBlockScreenshotsEnabled)
            }
        }
        splashScreen.setKeepOnScreenCondition {
            firstScreenRoute.value == null || settingsConfig.value == null
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            safeLet(firstScreenRoute.value, settingsConfig.value) { route, settings ->
                ApplyCorrectStatusNavBarColor(settings)
                Root(deviceThemeConfig, route, settings)
            }
        }
    }

    @Composable
    private fun ApplyCorrectStatusNavBarColor(
        settingsConfig: SettingsConfig
    ) {
        if (!window.decorView.isInEditMode) {
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = colorScheme.primary.toArgb()
            val view = LocalView.current
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !shouldUseDarkTheme(settingsConfig)
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !shouldUseDarkTheme(settingsConfig)
        }
    }

    private fun applyPrivacySettings(isBlockScreenshots: Boolean) {
        if (isBlockScreenshots) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AndroidActivityProvider.unregisterFilePicker()
    }

}