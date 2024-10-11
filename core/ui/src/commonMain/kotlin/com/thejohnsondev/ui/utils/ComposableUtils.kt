package com.thejohnsondev.ui.utils

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

fun WindowWidthSizeClass.isCompact() = this == WindowWidthSizeClass.Compact

fun WindowWidthSizeClass.isMedium() = this == WindowWidthSizeClass.Medium

fun WindowWidthSizeClass.isExpanded() = this == WindowWidthSizeClass.Expanded

fun WindowHeightSizeClass.isCompact() = this == WindowHeightSizeClass.Compact

fun WindowHeightSizeClass.isMedium() = this == WindowHeightSizeClass.Medium

fun WindowHeightSizeClass.isExpanded() = this == WindowHeightSizeClass.Expanded