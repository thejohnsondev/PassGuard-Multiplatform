package com.thejohnsondev.ui.utils

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.thejohnsondev.ui.designsystem.isBlurSupported

fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

fun PagerState.startOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtLeast(0f)
}

fun PagerState.endOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtMost(0f)
}

@Composable
fun Modifier.animateItemToBackgroundWithBlur(
    pageNumber: Int,
    pagerState: PagerState
): Modifier = this
    .zIndex(pageNumber * 10f)
    .graphicsLayer {
        val startOffset = pagerState.startOffsetForPage(pageNumber)
        translationX = size.width * (startOffset * .99f)

        val calculatedAlpha = (1f - startOffset * 2f)
        alpha = calculatedAlpha
        val scale = 1f - (startOffset * .2f)
        scaleX = scale
        scaleY = scale
    }
    .applyIf(isBlurSupported()) {
        blur(
            radiusX = (pagerState.startOffsetForPage(pageNumber) * 40f).coerceAtMost(40f).dp,
            radiusY = (pagerState.startOffsetForPage(pageNumber) * 40f).coerceAtMost(40f).dp,
        )
    }

fun Modifier.animateItemParallaxFromEndToCenter(
    pageNumber: Int,
    pagerState: PagerState,
    parallaxFactor: Float = 5f,
    applyScaleAnimation: Boolean = false,
): Modifier = this
    .graphicsLayer {
        val endOffset = pagerState.endOffsetForPage(pageNumber)
        translationX = size.width / parallaxFactor * (endOffset)
        alpha = (2f - endOffset) / 2f
        if (applyScaleAnimation) {
            val scale = 1f - (endOffset * .1f)
            scaleX = scale
            scaleY = scale
        }
    }