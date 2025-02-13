package org.thejohnsondev.vault

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.ComposeUIViewController
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.utils.ResDrawable
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
import org.koin.mp.KoinPlatform.getKoin
import org.thejohnsondev.vault.di.KoinInitializer
import org.thejohnsondev.vault.root.Root
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) {
    val getFirstScreenRouteUseCase: GetFirstScreenRouteUseCase = remember {
        getKoin().get()
    }
    val getSettingsUseCase: GetSettingsFlowUseCase = remember {
        getKoin().get()
    }
    val deviceThemeConfig: DeviceThemeConfig = remember {
        getKoin().get()
    }
    val coroutineScope = rememberCoroutineScope()
    val firstScreenRoute = remember { mutableStateOf<Routes?>(null) }
    val settingsConfig = remember { mutableStateOf<SettingsConfig?>(null) }

    LaunchedEffect(true) {
        coroutineScope.launch {
            firstScreenRoute.value = getFirstScreenRouteUseCase()
        }
        coroutineScope.launch {
            getSettingsUseCase.invoke().collect {
                settingsConfig.value = it
            }
        }
    }

    safeLet(firstScreenRoute.value, settingsConfig.value) { route, settings ->
        Root(deviceThemeConfig, route, settings)
    } ?: kotlin.run {
        Scaffold { paddingValues ->
            Surface(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues),
                color = Color.Black
            ) {
                Box {
                    Image(
                        modifier = Modifier.wrapContentSize()
                            .align(Alignment.Center)
                            .scale(1.4f),
                        imageVector = vectorResource(ResDrawable.ic_vault_108_gradient),
                        contentDescription = null // TODO add content description,
                    )
                }
            }
        }
    }
}