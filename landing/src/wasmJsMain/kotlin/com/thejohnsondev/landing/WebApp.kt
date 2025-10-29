package com.thejohnsondev.landing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import app.softwork.routingcompose.BrowserRouter
import app.softwork.routingcompose.Router
import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.model.settings.ThemeBrand
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.landing.components.WebScaffold
import com.thejohnsondev.landing.download.DownloadScreen
import com.thejohnsondev.landing.home.HomeScreen
import com.thejohnsondev.landing.privacy.PrivacyScreen
import com.thejohnsondev.ui.components.DebugConsole
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
import com.thejohnsondev.ui.designsystem.Size16
import com.thejohnsondev.ui.designsystem.colorscheme.VaultDefaultTheme
import kotlinx.browser.document


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        WebPage()
    }
}

@Composable
fun WebPage() {
    var router: Router? = remember { null }

    val appType = BuildKonfigProvider.getAppType()

    VaultDefaultTheme(
        darkTheme = false,
        dynamicColor = false,
        customTheme = ThemeBrand.TEAL,
        deviceThemeConfig = DeviceThemeConfig()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            WebScaffold(
                modifier = Modifier
                    .fillMaxSize()
            ) { paddingValues ->
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BrowserRouter("/") {
                        router = Router.current
                        route("/") { HomeScreen(paddingValues) }
                        route("/home") { HomeScreen(paddingValues) }
                        route("/download") { DownloadScreen() }
                        route("/privacy") { PrivacyScreen() }
                        noMatch { Text("404 – Page not found") }
                    }
                }
            }
            if (appType == AppType.DEV.name) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(Size16)
                ) {
                    DebugConsole()
                }
            }
            LaunchedEffect(router?.currentPath) {
                Logger.d("Navigated to: ${router?.currentPath}")
            }
        }
    }
}