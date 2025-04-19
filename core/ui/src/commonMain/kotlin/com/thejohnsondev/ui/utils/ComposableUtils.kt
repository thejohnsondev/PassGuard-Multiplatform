package com.thejohnsondev.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun WindowWidthSizeClass.isCompact() = this == WindowWidthSizeClass.Compact

fun WindowWidthSizeClass.isMedium() = this == WindowWidthSizeClass.Medium

fun WindowWidthSizeClass.isExpanded() = this == WindowWidthSizeClass.Expanded

fun WindowHeightSizeClass.isCompact() = this == WindowHeightSizeClass.Compact

fun WindowHeightSizeClass.isMedium() = this == WindowHeightSizeClass.Medium

fun WindowHeightSizeClass.isExpanded() = this == WindowHeightSizeClass.Expanded

@Composable
fun String.asPasswordFormatted(): AnnotatedString = buildAnnotatedString {
    forEach { char ->
        when {
            char.isDigit() -> {
                withStyle(
                    style = SpanStyle(color = MaterialTheme.colorScheme.primary)
                ) {
                    append(char)
                }
            }

            !char.isLetterOrDigit() -> {
                withStyle(
                    style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)
                ) {
                    append(char)
                }
            }

            else -> {
                append(char)
            }
        }
    }
}