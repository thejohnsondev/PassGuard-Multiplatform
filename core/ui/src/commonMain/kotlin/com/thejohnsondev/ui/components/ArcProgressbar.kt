package com.thejohnsondev.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.thejohnsondev.ui.designsystem.Size128
import com.thejohnsondev.ui.designsystem.Size8
import com.thejohnsondev.ui.designsystem.Text12
import com.thejohnsondev.ui.designsystem.Text64
import com.thejohnsondev.ui.utils.mapToStrengthLevelColor
import com.thejohnsondev.ui.utils.padding

@Composable
fun ArcProgressbar(
    modifier: Modifier = Modifier,
    progress: Float,
    maxProgress: Float = 1f,
    text: String,
) {
    var progressState by remember {
        mutableStateOf(progress)
    }

    LaunchedEffect(progress) {
        progressState = progress
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progressState,
        animationSpec = tween(durationMillis = 500),
        label = "Progress Animation"
    )

    Box(
        modifier = modifier.size(Size128),
        contentAlignment = Alignment.Center,
    ) {
        PointsProgress(
            progress = animatedProgress,
            maxProgress = maxProgress
        )
        ScoreText(
            modifier = Modifier.align(Alignment.Center)
                .fillMaxSize(),
            score = animatedProgress,
            text = text
        )
    }
}

@Composable
fun ScoreText(
    modifier: Modifier = Modifier,
    score: Float,
    text: String,
) {
    Box(
        modifier = modifier,
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = (score * 100).toInt().toString(),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = Text64
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = Text12
        )
    }
}

@Composable
fun BoxScope.PointsProgress(
    progress: Float,
    maxProgress: Float,
) {

    val start = 120f
    val end = 300f
    val thickness = Size8

    val emptyProgressColor = MaterialTheme.colorScheme.surfaceContainer
    val progressColor = progress.mapToStrengthLevelColor()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .aspectRatio(1f)
            .align(Alignment.Center),
        onDraw = {
            // Background Arc
            drawArc(
                color = emptyProgressColor,
                startAngle = start,
                sweepAngle = end,
                useCenter = false,
                style = Stroke(thickness.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height)
            )

            // Foreground Arc
            drawArc(
                color = progressColor,
                startAngle = start,
                sweepAngle = progress * end / maxProgress,
                useCenter = false,
                style = Stroke(thickness.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height)
            )

        }
    )
}