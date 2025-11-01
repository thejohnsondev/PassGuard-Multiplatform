package com.thejohnsondev.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.landing.components.WebNavBar
import com.thejohnsondev.landing.components.WebScaffold
import com.thejohnsondev.landing.home.HomeScreen

@Preview(widthDp = 1280)
@Composable
private fun NavBarNotCollapsedPreview() {
    PreviewTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            WebNavBar(
                modifier = Modifier
                    .align(Alignment.Center),
                navigateTo = {

                },
                isCollapsed = false,
            )
        }
    }
}

@Preview(widthDp = 1280)
@Composable
private fun NavBarCollapsedPreview() {
    PreviewTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            WebNavBar(
                modifier = Modifier
                    .align(Alignment.Center),
                navigateTo = {

                },
                isCollapsed = true,
            )
        }
    }
}

@Preview(widthDp = 1280)
@Composable
private fun HomeScreenPreview() {
    PreviewTheme {
        WebScaffold(
            modifier = Modifier
                .fillMaxWidth(),
            navigateTo = {}
        ) { paddings ->
            HomeScreen(paddings)
        }
    }
}

@Preview(widthDp = 1280)
@Composable
private fun HomeScreenDarkPreview() {
    PreviewTheme(
        darkTheme = true
    ) {
        WebScaffold(
            modifier = Modifier
                .fillMaxWidth()
        ) { paddings ->
            HomeScreen(paddings)
        }
    }
}