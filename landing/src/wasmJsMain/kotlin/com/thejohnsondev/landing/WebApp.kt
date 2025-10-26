package com.thejohnsondev.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import app.softwork.routingcompose.BrowserRouter
import app.softwork.routingcompose.Router
import com.thejohnsondev.common.AppType
import com.thejohnsondev.common.model.settings.ThemeBrand
import com.thejohnsondev.common.utils.BuildKonfigProvider
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.landing.download.DownloadScreen
import com.thejohnsondev.landing.home.HomeScreen
import com.thejohnsondev.landing.privacy.PrivacyScreen
import com.thejohnsondev.ui.components.DebugConsole
import com.thejohnsondev.ui.components.button.RoundedButton
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

    val scrollState = rememberScrollState()
    val scrollProgress by remember {
        derivedStateOf {
            if (scrollState.maxValue > 0)
                scrollState.value.toFloat() / scrollState.maxValue
            else 0f
        }
    }
    val navBarCollapsed by remember {
        derivedStateOf { scrollProgress > 0 }
    }

    val appType = BuildKonfigProvider.getAppType()


    LaunchedEffect(scrollState.value) {
        Logger.e("Scroll Progress: $scrollProgress")
    }

    LaunchedEffect(Unit) {
        Logger.d("WebPage Composable Launched")
    }

    VaultDefaultTheme(
        darkTheme = true, dynamicColor = false, customTheme = ThemeBrand.TEAL,
        deviceThemeConfig = DeviceThemeConfig()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState).padding(top = 72.dp)
                ) {
                    BrowserRouter("/") {
                        router = Router.current
                        route("/") { HomeScreen() }
                        route("/home") { HomeScreen() }
                        route("/download") { DownloadScreen() }
                        route("/privacy") { PrivacyScreen() }
                        noMatch { Text("404 – Page not found") }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.95f))
                        .align(Alignment.TopCenter)
                        .shadow(4.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    WebNavBar(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                            .clip(RoundedCornerShape(12.dp))
                            .shadow(8.dp),
                        navigateTo = { path -> router?.navigate(path) },
                        isCollapsed = navBarCollapsed
                    )
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
            }
        }
    }
}


@Composable
private fun WebNavBar(
    modifier: Modifier = Modifier,
    navigateTo: (String) -> Unit,
    isCollapsed: Boolean = false
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RoundedButton(onClick = { navigateTo("/home") }, text = "Home")
        RoundedButton(onClick = { navigateTo("/download") }, text = "Download")
        RoundedButton(onClick = { navigateTo("/privacy") }, text = "Privacy")
    }
}