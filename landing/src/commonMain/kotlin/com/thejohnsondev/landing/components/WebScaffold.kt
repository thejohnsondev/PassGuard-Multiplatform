package com.thejohnsondev.landing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.thejohnsondev.common.utils.Logger
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Size80
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun WebScaffold(
    modifier: Modifier = Modifier,
    webAppContainer: WebAppContainer.State,
    onAction: (WebAppContainer.Action) -> Unit,
    hazeState: HazeState = rememberHazeState(),
    navigateTo: (String) -> Unit,
    onScrollProgressChanged: ((Int) -> Unit)? = null,
    content: @Composable BoxScope.(PaddingValues) -> Unit,
) {
    val scrollState = rememberScrollState()
    val navBarCollapsed by remember {
        derivedStateOf { scrollState.value > 0 }
    }

    LaunchedEffect(scrollState.value) {
        onScrollProgressChanged?.invoke(scrollState.value)
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .hazeSource(state = hazeState)
        ) {
            content(
                PaddingValues(
                    top = Size80
                )
            )
        }
        WebNavBar(
            modifier = Modifier
                .padding(top = Size8)
                .align(Alignment.TopCenter),
            isCollapsed = navBarCollapsed,
            isDarkTheme = webAppContainer.isDarkTheme,
            hazeState = hazeState,
            navigateTo = navigateTo,
            onThemeButtonClick = {
                onAction(WebAppContainer.Action.ToggleDarkTheme)
            },
            onLanguageButtonClick = {
                // TODO implement language selection
            }
        )
    }
}
