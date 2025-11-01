package com.thejohnsondev.landing

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thejohnsondev.landing.components.WebAppContainer
import com.thejohnsondev.landing.components.WebScaffold
import com.thejohnsondev.landing.home.HomeScreen

@Preview(widthDp = 1280)
@Composable
private fun HomeScreenPreview() {
    PreviewTheme(
        darkTheme = false
    ) {
        WebScaffold(
            modifier = Modifier
                .fillMaxWidth(),
            navigateTo = {},
            webAppContainer = WebAppContainer.State(),
            onAction = {},
        ) { paddings ->
            HomeScreen(paddings)
        }
    }
}