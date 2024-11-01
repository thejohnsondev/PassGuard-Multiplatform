package org.thejohnsondev.vault

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.thejohnsondev.vault.navigation.Root

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        val getFirstScreenRoute = getKoin().get<GetFirstScreenRouteUseCase>()
        val deviceThemeConfig: DeviceThemeConfig = getKoin().get()
        val firstScreenRoute = mutableStateOf<String?>(null)
        lifecycleScope.launch {
            firstScreenRoute.value = getFirstScreenRoute()
        }
        splashScreen.setKeepOnScreenCondition {
            firstScreenRoute.value == null
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            firstScreenRoute.value?.let {
                Root(deviceThemeConfig, it)
            }
        }
    }

}