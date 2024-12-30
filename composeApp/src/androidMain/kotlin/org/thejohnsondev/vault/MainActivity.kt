package org.thejohnsondev.vault

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.thejohnsondev.vault.root.Root

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
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
                Root(deviceThemeConfig, route, settings)
            }
        }
    }

}