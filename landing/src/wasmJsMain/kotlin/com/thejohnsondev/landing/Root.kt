package com.thejohnsondev.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import app.softwork.routingcompose.BrowserRouter
import app.softwork.routingcompose.Router
import com.thejohnsondev.common.model.settings.ThemeBrand
import com.thejohnsondev.landing.download.DownloadScreen
import com.thejohnsondev.landing.home.HomeScreen
import com.thejohnsondev.landing.privacy.PrivacyScreen
import com.thejohnsondev.ui.components.button.RoundedButton
import com.thejohnsondev.ui.designsystem.DeviceThemeConfig
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
    var router: Router? = remember {
        null
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                WebNavBar(
                    navigateTo = { path ->
                        router?.navigate(path)
                    }
                )
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
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
            }
        }
    }
}

@Composable
private fun WebNavBar(
    navigateTo: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        RoundedButton(onClick = { navigateTo("/home") }, text = "Home")
        RoundedButton(onClick = { navigateTo("/download") }, text = "Download")
        RoundedButton(onClick = { navigateTo("/privacy") }, text = "Privacy")
    }
}