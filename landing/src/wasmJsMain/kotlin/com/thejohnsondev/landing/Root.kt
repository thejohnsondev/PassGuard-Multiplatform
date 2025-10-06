package com.thejohnsondev.landing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        App()
    }
}

@Composable
fun App() {
    val page = remember { mutableStateOf("home") }

    // TODO this is for testing only
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // simple nav
            Button(onClick = { page.value = "home" }) { Text("Home") }
            Button(onClick = { page.value = "download" }) { Text("Download") }
            Button(onClick = { page.value = "privacy" }) { Text("Privacy") }

            when (page.value) {
                "home" -> Text("Welcome to PassGuard — short pitch + screenshots go here")
                "download" -> Text("Download: click the button below")
                "privacy" -> Text("Privacy policy summary")
            }
        }
    }
}