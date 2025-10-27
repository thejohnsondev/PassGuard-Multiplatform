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
    hazeState: HazeState = rememberHazeState(),
    content: @Composable BoxScope.(PaddingValues) -> Unit,
) {
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

    LaunchedEffect(scrollState.value) {
        Logger.e("Scroll Progress: $scrollProgress")
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
            navigateTo = {
                // TODO Implement navigation
            },
            isCollapsed = navBarCollapsed,
            hazeState = hazeState
        )
    }
}
