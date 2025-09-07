package org.thejohnsondev.vault

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
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
import com.thejohnsondev.analytics.di.AnalyticsDependency
import com.thejohnsondev.analytics.test.DemoAnalyticsDependency
import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.navigation.Routes
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.common.utils.safeLet
import com.thejohnsondev.domain.CheckInstallIDUseCase
import com.thejohnsondev.domain.GetAnalyticsPropsUseCase
import com.thejohnsondev.domain.GetFirstScreenRouteUseCase
import com.thejohnsondev.domain.GetSettingsFlowUseCase
import com.thejohnsondev.domain.model.AnalyticsProps
import com.thejohnsondev.model.settings.SettingsConfig
import com.thejohnsondev.platform.di.PlatformDependency
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.utils.ResDrawable
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
import org.koin.mp.KoinPlatform.getKoin
import org.thejohnsondev.vault.di.KoinInitializer
import org.thejohnsondev.vault.root.Root
import platform.UIKit.UIColor
import vaultmultiplatform.core.ui.generated.resources.ic_vault_108_gradient

fun MainViewController(
    platformDependency: PlatformDependency,
    analyticsDependency: AnalyticsDependency
) = ComposeUIViewController(
    configure = {
        initKoin(platformDependency = platformDependency, analyticsDependency = analyticsDependency)
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
    val checkInstallIDUseCase = remember {
        getKoin().get<CheckInstallIDUseCase>()
    }
    val getAnalyticsPropsUseCase = remember {
        getKoin().get<GetAnalyticsPropsUseCase>()
    }
    val coroutineScope = rememberCoroutineScope()
    val firstScreenRoute = remember { mutableStateOf<Routes?>(null) }
    val settingsConfig = remember { mutableStateOf<SettingsConfig?>(null) }
    val checkInstallIDResult = remember { mutableStateOf<Boolean?>(null) }
    val analyticsProps = remember { mutableStateOf<AnalyticsProps?>(null) }

    LaunchedEffect(true) {
        coroutineScope.launch {
            firstScreenRoute.value = getFirstScreenRouteUseCase()
        }
        coroutineScope.launch {
            getSettingsUseCase.invoke().collect {
                settingsConfig.value = it
            }
        }
        coroutineScope.launch {
            checkInstallIDResult.value = checkInstallIDUseCase()
        }
        coroutineScope.launch {
            analyticsProps.value = getAnalyticsPropsUseCase()
        }
    }

    safeLet(
        firstScreenRoute.value,
        settingsConfig.value,
        analyticsProps.value,
        checkInstallIDResult.value
    ) { route, settings, analyticsProps, _ ->
        Root(deviceThemeConfig, route, settings, analyticsProps)
    } ?: run {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Box {
                Image(
                    modifier = Modifier.wrapContentSize()
                        .align(Alignment.Center)
                        .scale(1.4f),
                    imageVector = vectorResource(ResDrawable.ic_vault_108_gradient),
                    contentDescription = null
                )
            }
        }
    }
}.apply {
    view.backgroundColor = UIColor.blackColor()
}

private fun initKoin(
    platformDependency: PlatformDependency,
    analyticsDependency: AnalyticsDependency
) {
    KoinInitializer(
        platformDependency = getAppTypePlatformDependency(platformDependency),
        analyticsDependency = getAppTypeAnalyticsDependency(analyticsDependency)
    ).init()
}

private fun getAppTypePlatformDependency(
    platformDependency: PlatformDependency
): PlatformDependency {
    return platformDependency
}

private fun getAppTypeAnalyticsDependency(
    analyticsDependency: AnalyticsDependency
): AnalyticsDependency {
    return when (AppType.from(BuildKonfigProvider.getAppType())) {
        AppType.DEMO -> {
            DemoAnalyticsDependency()
        }
        AppType.DEV,
        AppType.PROD-> {
            analyticsDependency
        }
    }
}