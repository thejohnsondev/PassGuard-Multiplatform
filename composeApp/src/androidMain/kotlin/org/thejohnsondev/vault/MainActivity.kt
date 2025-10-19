package org.thejohnsondev.vault

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.thejohnsondev.common.model.settings.SettingsConfig
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.CheckInstallIDUseCase
import com.thejohnsondev.domain.GetAnalyticsPropsUseCase
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.model.AnalyticsProps
import com.thejohnsondev.platform.filemanager.AndroidActivityProvider
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.utils.applyIf
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.thejohnsondev.vault.root.Root
import org.thejohnsondev.vault.root.shouldUseDarkTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AndroidActivityProvider.registerActivity(this)
        val getFirstScreenRoute = getKoin().get<GetFirstScreenRouteUseCase>()
        val getSettingsUseCase = getKoin().get<GetSettingsFlowUseCase>()
        val checkInstallIDUseCase = getKoin().get<CheckInstallIDUseCase>()
        val getAnalyticsPropsUseCase = getKoin().get<GetAnalyticsPropsUseCase>()
        val deviceThemeConfig: DeviceThemeConfig = getKoin().get()
        val firstScreenRoute = mutableStateOf<Routes?>(null)
        val settingsConfig = mutableStateOf<SettingsConfig?>(null)
        val checkInstallIDResult = mutableStateOf<Boolean?>(null)
        val analyticsProps = mutableStateOf<AnalyticsProps?>(null)
        lifecycleScope.launch {
            firstScreenRoute.value = getFirstScreenRoute()
        }
        lifecycleScope.launch {
            getSettingsUseCase.invoke().collect {
                settingsConfig.value = it
                applyPrivacySettings(it.privacySettings.isBlockScreenshotsEnabled)
            }
        }
        lifecycleScope.launch {
            analyticsProps.value = getAnalyticsPropsUseCase()
        }
        lifecycleScope.launch {
            checkInstallIDResult.value = checkInstallIDUseCase()
        }
        splashScreen.setKeepOnScreenCondition {
            firstScreenRoute.value == null || settingsConfig.value == null
        }

        setContent {
            safeLet(
                firstScreenRoute.value,
                settingsConfig.value,
                analyticsProps.value,
                checkInstallIDResult.value
            ) { route, settings, analyticsProps, _ ->
                ApplyCorrectStatusNavBarColor(settings)
                Root(
                    modifier = Modifier
                        .applyIf(android.os.Build.VERSION.SDK_INT == 29) {
                            safeContentPadding()
                        },
                    deviceThemeConfig = deviceThemeConfig,
                    firstScreenRoute = route,
                    settingsConfig = settings,
                    analyticsProps = analyticsProps
                )
            }
        }
    }

    @Composable
    private fun ApplyCorrectStatusNavBarColor(
        settingsConfig: SettingsConfig
    ) {
        if (!window.decorView.isInEditMode) {
            val view = LocalView.current
            val isLightTheme = !shouldUseDarkTheme(settingsConfig)
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = isLightTheme
                isAppearanceLightNavigationBars = isLightTheme
            }
            WindowCompat.setDecorFitsSystemWindows(window, false)
            setSystemBarsAppearanceForAndroid29And30(window, isLightTheme)
            setWindowFlagsForAndroid31AndAbove(window)
        }
    }

    private fun setSystemBarsAppearanceForAndroid29And30(window: Window, isLightTheme: Boolean) {
        if (Build.VERSION.SDK_INT in Build.VERSION_CODES.Q..Build.VERSION_CODES.R) {
            @Suppress("DEPRECATION")
            run {
                window.statusBarColor = if (isLightTheme) Color.WHITE else Color.BLACK
                window.navigationBarColor = if (isLightTheme) Color.WHITE else Color.BLACK
            }
        }
    }

    private fun setWindowFlagsForAndroid31AndAbove(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
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